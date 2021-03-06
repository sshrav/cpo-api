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
package org.synchronoss.cpo.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;
import org.synchronoss.cpo.CpoAdapter;
import org.synchronoss.cpo.CpoAdapterFactory;
import org.synchronoss.cpo.CpoNativeFunction;
import org.synchronoss.cpo.CpoWhere;

/**
 * BlobTest is a JUnit test class for testing the JdbcAdapter class Constructors
 *
 * @author david berry
 */
public class NativeExpressionTest extends TestCase {

  private CpoAdapter cpoAdapter = null;
  private ArrayList<ValueObject> al = new ArrayList<>();

  /**
   * Creates a new RollbackTest object.
   *
   * @param name DOCUMENT ME!
   */
  public NativeExpressionTest() {
  }

  /**
   * <code>setUp</code> Load the datasource from the properties in the property file jdbc_en_US.properties
   */
  @Override
  public void setUp() {
    String method = "setUp:";

    try {
      cpoAdapter = CpoAdapterFactory.getCpoAdapter(JdbcStatics.ADAPTER_CONTEXT_JDBC);
      assertNotNull(method + "CpoAdapter is null", cpoAdapter);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
    ValueObject vo = new ValueObjectBean(1);
    vo.setAttrVarChar("Test");
    vo.setAttrSmallInt(1);
    al.add(vo);
    al.add(new ValueObjectBean(2));
    al.add(new ValueObjectBean(3));
    al.add(new ValueObjectBean(4));
    al.add(new ValueObjectBean(5));
    al.add(new ValueObjectBean(-6));
    try {
      cpoAdapter.insertObjects("TestOrderByInsert", al);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  /**
   * DOCUMENT ME!
   */
  @Override
  public void tearDown() {
    String method = "tearDown:";
    try {
      cpoAdapter.deleteObjects("TestOrderByDelete", al);

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
    cpoAdapter = null;
  }

  /**
   * DOCUMENT ME!
   */
  public void testNativeOrWhere() {
    String method = "testNativeOrWhere:";
    Collection<ValueObject> col;
    CpoWhere cw = null;
    CpoWhere cw1 = null;
    CpoWhere cw2 = null;


    try {
      ArrayList<CpoNativeFunction> cnqAl = new ArrayList<>();


      cnqAl.add(new CpoNativeFunction("__CPO_WHERE__", "WHERE ID = 2 OR ID = 3"));

      ValueObject valObj = new ValueObjectBean(3);
      col = cpoAdapter.retrieveBeans("TestWhereRetrieve", valObj, valObj, null, null, cnqAl);

      assertTrue("Col size is " + col.size(), col.size() == 2);

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testNullNative() {
    String method = "testNullNative:";
    Collection<ValueObject> col;
    CpoWhere cw = null;
    CpoWhere cw1 = null;
    CpoWhere cw2 = null;


    try {
      ArrayList<CpoNativeFunction> cnqAl = new ArrayList<>();



      cnqAl.add(new CpoNativeFunction("__CPO_WHERE__", null));

      ValueObject valObj = new ValueObjectBean(3);
      col = cpoAdapter.retrieveBeans("TestWhereRetrieve", valObj, valObj, null, null, cnqAl);

      assertTrue("Col size is " + col.size(), col.size() == 6);

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }
}
