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

import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.synchronoss.cpo.CpoAdapter;
import org.synchronoss.cpo.CpoAdapterFactory;
import org.synchronoss.cpo.CpoResultSet;
import org.synchronoss.cpo.CpoTrxAdapter;

/**
 * RetrieveObjectTest is a JUnit test class for testing the JdbcAdapter class Constructors
 *
 * @author david berry
 */
public class RetrieveObjectTest extends TestCase {

  private static Logger logger = LoggerFactory.getLogger(RetrieveObjectTest.class.getName());
  private CpoAdapter cpoAdapter = null;
  private ArrayList<ValueObject> al = new ArrayList<ValueObject>();

  public RetrieveObjectTest(String name) {
    super(name);
  }

  /**
   * <code>setUp</code> Load the datasource from the properties in the property file jdbc_en_US.properties
   *
   * @author david berry
   * @version '$Id: RetrieveObjectTest.java,v 1.6 2006/01/30 19:09:23 dberry Exp $'
   */
  @Override
  public void setUp() {
    String method = "setUp:";

    try {
      cpoAdapter = CpoAdapterFactory.getCpoAdapter(JdbcStatics.ADAPTER_CONTEXT_JDBC);
      assertNotNull(method + "IdoAdapter is null", cpoAdapter);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
    ValueObject vo = new ValueObject(1);
    vo.setAttrVarChar("Test");
    al.add(vo);
    al.add(new ValueObject(2));
    al.add(new ValueObject(3));
    al.add(new ValueObject(4));
    al.add(new ValueObject(5));
    al.add(new ValueObject(6));
    al.add(new ValueObject(7));
    al.add(new ValueObject(8));
    al.add(new ValueObject(9));
    al.add(new ValueObject(10));
    try {
      cpoAdapter.insertObjects("TestOrderByInsert", al);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testRetrieveObjects() {
    String method = "testRetrieveObjects:";
    Collection<ValueObject> col;


    try {
      ValueObject valObj = new ValueObject();
      col = cpoAdapter.retrieveBeans(null, valObj);
      assertTrue("Col size is " + col.size(), col.size() == al.size());

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testIsClosed() {
    String method = "testIsClosed:";
    Collection<ValueObject> col;
    CpoTrxAdapter trx = null;

    try {
      trx = cpoAdapter.getCpoTrxAdapter();
      trx.isClosed();
      ValueObject valObj = new ValueObject();
      col = trx.retrieveBeans(null, valObj);
      assertTrue("Col size is " + col.size(), col.size() == al.size());
      trx.commit();
    } catch (Exception e) {
      fail(method + e.getMessage());
    } finally {
      try {
        trx.close();
      } catch (Exception e) {
      }
    }
  }

  public void testRetrieveObjectsNoWaitSize2() {
    String method = "testRetrieveObjectsNoWaitSize2:";
    CpoResultSet<ValueObject> crs;
    int count = 0;

    try {
      ValueObject valObj = new ValueObject();
      crs = cpoAdapter.retrieveObjects(null, valObj, null, null, null, valObj, 2);
      logger.debug("Returned from retrieveObjects");
      for (ValueObject vo : crs) {
        if (vo != null) {
          count++;
        }
        logger.debug("Retrieved Object #" + count);
      }
      assertTrue("Result size is " + count, count == al.size());

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testRetrieveObjectsNoWaitSize9() {
    String method = "testRetrieveObjectsNoWaitSize9:";
    CpoResultSet<ValueObject> crs;
    int count = 0;

    try {
      ValueObject valObj = new ValueObject();
      crs = cpoAdapter.retrieveObjects(null, valObj, null, null, null, valObj, 9);
      for (ValueObject vo : crs) {
        if (vo != null) {
          count++;
        }
      }
      assertTrue("Result size is " + count, count == al.size());

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testRetrieveObjectsNoWaitSize10() {
    String method = "testRetrieveObjectsNoWaitSize10:";
    CpoResultSet<ValueObject> crs;
    int count = 0;

    try {
      ValueObject valObj = new ValueObject();
      crs = cpoAdapter.retrieveObjects(null, valObj, null, null, null, valObj, 10);
      for (ValueObject vo : crs) {
        if (vo != null) {
          count++;
        }
      }
      assertTrue("Result size is " + count, count == al.size());

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testRetrieveObjectsNoWaitSize11() {
    String method = "testRetrieveObjectsNoWaitSize11:";
    CpoResultSet<ValueObject> crs;
    int count = 0;

    try {
      ValueObject valObj = new ValueObject();
      crs = cpoAdapter.retrieveObjects(null, valObj, null, null, null, valObj, 11);
      for (ValueObject vo : crs) {
        if (vo != null) {
          count++;
        }
      }
      assertTrue("Result size is " + count, count == al.size());

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testConnectionBusy() {
    String method = "testConnectionBusy:";
    CpoResultSet<ValueObject> crs;
    int count = 0;
    CpoTrxAdapter trx = null;

    try {
      trx = cpoAdapter.getCpoTrxAdapter();

      ValueObject valObj = new ValueObject();
      crs = trx.retrieveObjects(null, valObj, null, null, null, valObj, 2);

      //start this trx
      for (ValueObject vo : crs) {
        if (vo != null) {
          count++;
        }
        break;
      }

      // Let's see if it lets me do two trxs at once
      try {
        trx.retrieveBeans(null, valObj);
        fail(method + "Cpo allowed me to reuse a busy connection");
      } catch (Exception busy) {
        // THis should happen
        logger.debug("Got the busy exception like expected");
      }

      //cleanup the first trx
      for (ValueObject vo : crs) {
        if (vo != null) {
          count++;
        }
      }
      assertTrue("Result size is " + count, count == al.size());

    } catch (Exception e) {
      fail(method + e.getMessage());
    } finally {
      try {
        trx.close();
      } catch (Exception e) {
      }
    }
  }

  public void testRetrieveObjectsNoWaitSize20() {
    String method = "testRetrieveObjectsNoWaitSize20:";
    CpoResultSet<ValueObject> crs;
    int count = 0;

    try {
      ValueObject valObj = new ValueObject();
      crs = cpoAdapter.retrieveObjects(null, valObj, null, null, null, valObj, 20);
      logger.debug("Returned from retrieveObjects");
      for (ValueObject vo : crs) {
        if (vo != null) {
          count++;
        }
        logger.debug("Retrieved Object #" + count);
      }
      assertTrue("Result size is " + count, count == al.size());

    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testRetrieveObject() {

    String method = "testRetrieveObject:";
    ValueObject vo = new ValueObject(1);
    ValueObject rvo;

    try {
      rvo = cpoAdapter.retrieveObject(vo);
      assertNotNull(method + "Returned Value object is null");
      assertNotSame(method + "ValueObjects are the same", vo, rvo);
      assertEquals(method + "Strings are not the same", rvo.getAttrVarChar(), "Test");
      if (rvo.getAttrVarChar().equals(vo.getAttrVarChar())) {
        fail(method + "ValueObjects are the same");
      }
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testNullRetrieveObject() {

    String method = "testNullRetrieveObject:";
    ValueObject vo = new ValueObject(100);
    ValueObject rvo;

    try {
      rvo = cpoAdapter.retrieveObject(vo);
      assertNull(method + "Returned Value object is Not Null", rvo);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

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
}