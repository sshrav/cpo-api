/*
 *  Copyright (C) 2003-2012 David E. Berry
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *  A copy of the GNU Lesser General Public License may also be found at 
 *  http://www.gnu.org/licenses/lgpl.txt
 *
 */
package org.synchronoss.cpo.jdbc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;
import org.synchronoss.cpo.CpoAdapter;
import org.synchronoss.cpo.CpoAdapterBean;
import org.synchronoss.cpo.CpoAdapterFactory;
import org.synchronoss.cpo.jdbc.meta.JdbcCpoMetaAdapter;

/**
 * RetrieveObjectTest is a JUnit test class for testing the JdbcAdapter class Constructors
 *
 * @author david berry
 */
public class InsertObjectTest extends TestCase {

  private ArrayList<ValueObject> al = new ArrayList<ValueObject>();
  private CpoAdapter cpoAdapter = null;
  private CpoAdapter readAdapter = null;
  private JdbcCpoMetaAdapter metaAdapter = null;

  public InsertObjectTest(String name) {
    super(name);
  }

  /**
   * <code>setUp</code> Load the datasource from the properties in the property file jdbc_en_US.properties
   *
   * @author david berry
   * @version '$Id: InsertObjectTest.java,v 1.3 2006/01/30 19:09:23 dberry Exp $'
   */
  @Override
  public void setUp() {
    String method = "setUp:";

    try {
      cpoAdapter = new CpoAdapterBean(CpoAdapterFactory.getCpoAdapter(JdbcStatics.ADAPTER_CONTEXT));
      assertNotNull(method + "IdoAdapter is null", cpoAdapter);
      metaAdapter = (JdbcCpoMetaAdapter) cpoAdapter.getCpoMetaAdapter();
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
    try {
      readAdapter = new CpoAdapterBean(CpoAdapterFactory.getCpoAdapter(JdbcStatics.ADAPTER_CONTEXT));
      assertNotNull(method + "IdoAdapter is null", readAdapter);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testInsertObject() {
    String method = "testInsertObject:";
    ValueObject valObj = new ValueObject(5);

    valObj.setAttrVarChar("testInsert");
    valObj.setAttrInteger(3);
    Timestamp ts = new Timestamp(System.currentTimeMillis());

    if (!metaAdapter.isSupportsMillis()) {
      ts.setNanos(0);
    }

    valObj.setAttrDatetime(ts);

    valObj.setAttrBit(true);

    al.add(valObj);

    try {
      cpoAdapter.insertObject(valObj);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }

    try {
      ValueObject vo = readAdapter.retrieveObject(null, valObj, valObj, null, null);
      assertTrue("Ids do not match", vo.getId() == valObj.getId());
      assertTrue("Integers do not match", vo.getAttrInteger() == valObj.getAttrInteger());
      assertEquals("Strings do not match", vo.getAttrVarChar(), valObj.getAttrVarChar());
      assertEquals("Timestamps do not match", vo.getAttrDatetime(), valObj.getAttrDatetime());
      assertTrue("boolean not stored correctly", vo.getAttrBit());

    } catch (Exception e) {
      fail(method + e.getMessage());
    }


  }

  public void testInsertObjects() {

    String method = "testInsertObjects:";
    ValueObject vo = new ValueObject(1);
    vo.setAttrVarChar("Test");

    al.add(vo);
    al.add(new ValueObject(2));
    al.add(new ValueObject(3));
    al.add(new ValueObject(4));
    try {
      long inserts = cpoAdapter.insertObjects(al);
      assertEquals("inserts performed do not equal inserts requested", inserts, 4);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }

    try {
      Collection<ValueObject> col = readAdapter.retrieveBeans(null, vo);

      assertTrue(method + "Invalid number of objects returned", col.size() == al.size());
    } catch (Exception e) {
      fail(method + e.getMessage());
    }


  }

  @Override
  public void tearDown() {
    String method = "tearDown:";
    try {
      cpoAdapter.deleteObjects(al);

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
    cpoAdapter = null;
    readAdapter = null;
  }
}