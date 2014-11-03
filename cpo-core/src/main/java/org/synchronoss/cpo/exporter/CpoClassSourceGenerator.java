/*
 * Copyright (C) 2003-2012 David E. Berry
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * A copy of the GNU Lesser General Public License may also be found at
 * http://www.gnu.org/licenses/lgpl.txt
 */
package org.synchronoss.cpo.exporter;

import org.synchronoss.cpo.CpoException;
import org.synchronoss.cpo.MetaVisitor;
import org.synchronoss.cpo.meta.CpoMetaDescriptor;
import org.synchronoss.cpo.meta.domain.*;

/**
 * The CpoClassSourceGenerator generates java source code to define the cpo classes.
 *
 * @author Michael Bellomo
 * @since 4/17/12
 */
public class CpoClassSourceGenerator implements MetaVisitor {

  private static final String ATTR_PREFIX = "ATTR_";
  private static final String FG_PREFIX = "FG_";
  private static final String VAR_PREFIX = "_";

  protected CpoMetaDescriptor metaDescriptor;
  protected String className = null;
  protected StringBuilder header = new StringBuilder();
  protected StringBuilder attributeStatics = new StringBuilder();
  protected StringBuilder functionGroupStatics = new StringBuilder();
  protected StringBuilder properties = new StringBuilder();
  protected StringBuilder constructor = new StringBuilder();
  protected StringBuilder gettersSetters = new StringBuilder();
  protected StringBuilder equals = new StringBuilder();
  protected StringBuilder hashCode = new StringBuilder();
  protected StringBuilder toString = new StringBuilder();
  protected StringBuilder footer = new StringBuilder();

  public CpoClassSourceGenerator(CpoMetaDescriptor metaDescriptor) {
    this.metaDescriptor = metaDescriptor;
  }

  /**
   * Resets all of the buffers.
   *
   * This is needed if you intend to reuse the visitor. This is always called by visit(CpoClass)
   */
  protected void reset() {
    attributeStatics = new StringBuilder();
    functionGroupStatics = new StringBuilder();
    properties = new StringBuilder();
    constructor = new StringBuilder();
    gettersSetters = new StringBuilder();
    equals = new StringBuilder();
    hashCode = new StringBuilder();
    toString = new StringBuilder();
    footer = new StringBuilder();
  }

  public String getClassName() {
    return className;
  }

  public String getSourceCode() {
    StringBuilder source = new StringBuilder();

    source.append("/* \n");
    source.append(" *  This class auto-generated by " + this.getClass().getName() + "\n");
    source.append(" */\n");
    source.append(header);
    source.append("\n");
    source.append("  /* Attribute name statics */\n");
    source.append(attributeStatics);
    source.append("\n");
    source.append("  /* Function group statics */\n");
    source.append(functionGroupStatics);
    source.append("\n");
    source.append("  /* Properties */\n");
    source.append(properties);
    source.append("\n");
    source.append("  /* Constructor */\n");
    source.append(constructor);
    source.append("\n");
    source.append("  /* Getters and Setters */\n");
    source.append(gettersSetters);

    // generate equals()
    source.append("  public boolean equals(Object o) {\n");
    source.append("    if (this == o)\n");
    source.append("      return true;\n");
    source.append("    if (o == null || getClass() != o.getClass())\n");
    source.append("      return false;\n");
    source.append("\n");
    source.append("    " + className + " that = (" + className + ")o;\n");
    source.append("\n");
    source.append(equals);
    source.append("\n");
    source.append("    return true;\n");
    source.append("  }\n\n");

    // generate hashCode()
    source.append("  public int hashCode() {\n");
    source.append("    int result = 0;\n");
    source.append("    result = 31 * result + getClass().getName().hashCode();\n");
    source.append(hashCode);
    source.append("    return result;\n");
    source.append("  }\n\n");

    // generate toString()
    source.append("  public String toString() {\n");
    source.append("    StringBuilder str = new StringBuilder();\n");
    source.append(toString);
    source.append("    return str.toString();\n");
    source.append("  }\n");

    source.append(footer);
    source.append("\n");

    return source.toString();
  }

  /**
   * Returns the name of the class to use
   */
  protected String generateClassName(CpoClass cpoClass) {
    String className = cpoClass.getName();
    if (className.lastIndexOf(".") != -1) {
      className = className.substring(className.lastIndexOf(".") + 1);
    }
    return className;
  }

  @Override
  public void visit(CpoClass cpoClass) {

    // reset all the buffers
    reset();

    className = generateClassName(cpoClass);

    // generate class header
    if (cpoClass.getName().lastIndexOf(".") != -1) {
      String packageName = cpoClass.getName().substring(0, cpoClass.getName().lastIndexOf("."));
      header.append("package " + packageName + ";\n\n");
    }

    // generate class declaration
    header.append("public class " + className + " implements java.io.Serializable {\n");

    // footer
    footer.append("}\n");

    // generate constructor
    constructor.append("  public " + className + "() {\n");
    constructor.append("  }\n");
  }

  @Override
  public void visit(CpoAttribute cpoAttribute) {

    String attName = scrubName(cpoAttribute.getJavaName());

    if (cpoAttribute.getTransformClassName()!=null && cpoAttribute.getTransformInMethod()==null) {
      try {
        cpoAttribute.loadRunTimeInfo(metaDescriptor, null);
      } catch (Exception e) {

      }
    }

    // the getter name is get concatenated with the camel case of the attribute name
    String getterName;
    String setterName;
    if (attName.length() > 1) {
      getterName = ("get" + attName.substring(0, 1).toUpperCase() + attName.substring(1) + "()");
      setterName = ("set" + attName.substring(0, 1).toUpperCase() + attName.substring(1));
    } else {
      getterName = ("get" + attName.toUpperCase() + "()");
      setterName = ("set" + attName.toUpperCase());
    }

    try {
      Class<?> attClass = metaDescriptor.getDataTypeJavaClass(cpoAttribute);
      String attClassName = metaDescriptor.getDataTypeName(cpoAttribute);

      // generate attribute statics
      String staticName = ATTR_PREFIX + attName.toUpperCase();
      attributeStatics.append("  public final static String " + staticName + " = \"" + attName + "\";\n");

      // generate property declarations
      properties.append("  private " + attClassName + " " + VAR_PREFIX + attName + ";\n");

      // generate getter
      gettersSetters.append("  public " + attClassName + " " + getterName + " {\n");
      gettersSetters.append("    return " + VAR_PREFIX + attName + ";\n");
      gettersSetters.append("  }\n\n");

      // generate setter
      gettersSetters.append("  public void " + setterName + "(" + attClassName + " " + attName + ") {\n");
      gettersSetters.append("    " + VAR_PREFIX + attName + " = " + attName + ";\n");
      gettersSetters.append("  }\n\n");

      // equals()
      if (attClass.isPrimitive()) {
        // primitive type, use ==
        equals.append("    if (" + getterName + " != that." + getterName + ")\n");
      } else if (attClass.isArray()) {
        // array type, use Array.equals()
        equals.append("    if (!java.util.Arrays.equals(" + getterName + ", that." + getterName + "))\n");
      } else {
        // object, use .equals
        equals.append("    if (" + getterName + " != null ? !" + getterName + ".equals(that." + getterName + ") : that." + getterName + " != null)\n");
      }
      equals.append("      return false;\n");

      // hashCode()
      if (attClass.isPrimitive()) {
        // primitive type, need some magic
        hashCode.append("    result = 31 * result + (String.valueOf(" + getterName + ").hashCode());\n");
      } else if (attClass.isArray()) {
        // array type, use Array.hashCode()
        hashCode.append("    result = 31 * result + (" + getterName + "!= null ? java.util.Arrays.hashCode(" + getterName + ") : 0);\n");
      } else {
        hashCode.append("    result = 31 * result + (" + getterName + " != null ? " + getterName + ".hashCode() : 0);\n");
      }

      // toString()
      toString.append("    str.append(\"" + attName + " = \" + " + getterName + " + \"\\n\");\n");
    } catch(CpoException ce) {

    }
  }

  @Override
  public void visit(CpoFunctionGroup cpoFunctionGroup) {

    // generate statics for function group
    String fgName = cpoFunctionGroup.getName();
    if (fgName == null) {
      fgName = "NULL";
    }
    fgName = scrubName(fgName);

    String staticName = FG_PREFIX + cpoFunctionGroup.getType() + "_" + fgName.toUpperCase();

    if (cpoFunctionGroup.getName() == null) {
      functionGroupStatics.append("  public final static String " + staticName + " = null;\n");
    } else {
      functionGroupStatics.append("  public final static String " + staticName + " = \"" + fgName + "\";\n");
    }
  }

  @Override
  public void visit(CpoFunction cpoFunction) {
    // nothing to do
  }

  @Override
  public void visit(CpoArgument cpoArgument) {
    // nothing to do
  }

  protected String scrubName(String name) {
    return name.replaceAll("[^0-9a-zA-Z_]", "_");
  }
}
