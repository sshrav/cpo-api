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
package org.synchronoss.cpo.jdbc.exporter;

import junit.framework.TestCase;
import org.slf4j.*;
import org.synchronoss.cpo.*;
import org.synchronoss.cpo.core.cpoCoreMeta.*;
import org.synchronoss.cpo.exporter.*;
import org.synchronoss.cpo.jdbc.*;
import org.synchronoss.cpo.jdbc.meta.JdbcCpoMetaDescriptor;
import org.synchronoss.cpo.meta.domain.CpoClass;

import javax.tools.*;
import java.io.*;
import java.util.Arrays;

/**
 * JUnit test class for testing the ExporterTest
 *
 * @author Michael Bellomo
 */
public class ExporterTest extends TestCase {

  private static final Logger logger = LoggerFactory.getLogger(ExporterTest.class);

  private CpoAdapter cpoAdapter = null;
  private JdbcCpoMetaDescriptor metaDescriptor = null;

  public ExporterTest() {
  }

  @Override
  public void setUp() {
    String method = "setUp:";

    try {
      cpoAdapter = CpoAdapterFactory.getCpoAdapter(JdbcStatics.ADAPTER_CONTEXT_JDBC);
      assertNotNull(method + "CpoAdapter is null", cpoAdapter);
      metaDescriptor = (JdbcCpoMetaDescriptor) cpoAdapter.getCpoMetaDescriptor();
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  @Override
  public void tearDown() {
    cpoAdapter = null;
  }

  public void testXmlExport() {
    logger.debug("testXmlExport");
    try {
      MetaXmlObjectExporter metaXmlObjectExporter = new JdbcMetaXmlObjectExporter(metaDescriptor);
      for (CpoClass cpoClass : metaDescriptor.getCpoClasses()) {
        cpoClass.acceptMetaDFVisitor(metaXmlObjectExporter);
      }
      CpoMetaDataDocument doc = metaXmlObjectExporter.getCpoMetaDataDocument();

      // doc better be valid
      logger.debug("validating doc");
      assertTrue(doc.validate());

      // make sure it saved the data right

      // should be 2 classes in here
      assertEquals(2, doc.getCpoMetaData().getCpoClassArray().length);

      boolean found = false;
      for (CtClass ctClass : doc.getCpoMetaData().getCpoClassArray()) {
        // validate the ValueObject
        if (ctClass.getName().equals(ValueObject.class.getName())) {
          found = true;
        }
      }
      assertTrue(found);
    } catch (CpoException e) {
      fail(e.getMessage());
    }
    logger.debug("testXmlExport complete");
  }

  public void testClassSourceExport() {
    logger.debug("testClassSourceExport");
    try {
      CpoClassSourceGenerator classSourceGenerator = new CpoClassSourceGenerator(metaDescriptor);

      logger.debug("Generating java source");
      CpoClass cpoClass = metaDescriptor.getMetaClass(new ValueObject());
      cpoClass.acceptMetaDFVisitor(classSourceGenerator);
      String classSource = classSourceGenerator.getSourceCode();

      // validate that we got something
      assertNotNull(classSource);
      assertFalse(classSource.isEmpty());

      // write the file
      File javaFile = new File("target", ValueObject.class.getSimpleName() + ".java");
      logger.debug("Saving class source to " + javaFile.getAbsolutePath());
      FileWriter cw = new FileWriter(javaFile);
      cw.write(classSourceGenerator.getSourceCode());
      cw.flush();
      cw.close();

      // let's try to compile the file
      logger.debug("Compiling class source");
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
      Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFile));
      boolean result = compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();

      // validate the result
      assertTrue(result);

    } catch (Exception e) {
      fail(e.getMessage());
    }
    logger.debug("testClassSourceExport complete");
  }
}