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
import java.util.*;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.synchronoss.cpo.CpoAdapter;
import org.synchronoss.cpo.CpoAdapterBean;
import org.synchronoss.cpo.CpoAdapterFactory;
import org.synchronoss.cpo.CpoWhere;
import org.synchronoss.cpo.helper.ExceptionHelper;

/**
 * DeleteObjectTest is a JUnit test class for testing the JdbcAdapter deleteObject method
 *
 * @author david berry
 */
public class DeleteObjectTest extends TestCase {

  private static Logger logger = LoggerFactory.getLogger(DeleteObjectTest.class.getName());
  private ArrayList<ValueObject> al = new ArrayList<ValueObject>();
  private CpoAdapter jdbcIdo_ = null;
  private boolean hasMilliSupport = true;

  public DeleteObjectTest(String name) {
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
    ResourceBundle b = PropertyResourceBundle.getBundle(JdbcStatics.PROP_FILE, Locale.getDefault(), this.getClass().getClassLoader());

    hasMilliSupport = new Boolean(b.getString(JdbcStatics.PROP_DB_MILLI_SUPPORTED).trim());

    try {
      jdbcIdo_ = new CpoAdapterBean(CpoAdapterFactory.getCpoAdapter(JdbcStatics.ADAPTER_CONTEXT));
      assertNotNull(method + "IdoAdapter is null", jdbcIdo_);
    } catch (Exception e) {
      fail(method + e.getMessage());
    }
  }

  public void testDeleteObject() {
    String method = "testDeleteObject:";
    ValueObject valObj = new ValueObject(5);

    valObj.setAttrVarChar("testDelete");
    valObj.setAttrInteger(3);
    Timestamp ts = new Timestamp(System.currentTimeMillis());

    if (!hasMilliSupport) {
      ts.setNanos(0);
    }

    valObj.setAttrDatetime(ts);

    valObj.setAttrBit(true);

    al.add(valObj);

    try {
      jdbcIdo_.insertObject(valObj);
    } catch (Exception e) {
      logger.error(ExceptionHelper.getLocalizedMessage(e));
      fail(method + e.getMessage());
    }

    // try the where on the delete, should delete 0
    try {
      List<CpoWhere> cws = new ArrayList<CpoWhere>();
      cws.add(jdbcIdo_.newWhere(CpoWhere.LOGIC_AND, "id", CpoWhere.COMP_EQ, new Integer(2)));
      long deleted = jdbcIdo_.deleteObject(null, valObj, cws, null, null);
      assertEquals("Should not have deleted anything", 0, deleted);
    } catch (Exception e) {
      logger.error(ExceptionHelper.getLocalizedMessage(e));
      fail(method + e.getMessage());
    }

    // try the where on the delete, should delete 1
    try {
      List<CpoWhere> cws = new ArrayList<CpoWhere>();
      cws.add(jdbcIdo_.newWhere(CpoWhere.LOGIC_OR, "id", CpoWhere.COMP_EQ, new Integer(2)));
      long deleted = jdbcIdo_.deleteObject(null, valObj, cws, null, null);
      assertEquals("Should have deleted 1", 1, deleted);
    } catch (Exception e) {
      logger.error(ExceptionHelper.getLocalizedMessage(e));
      fail(method + e.getMessage());
    }



  }

  @Override
  public void tearDown() {
    String method = "tearDown:";
    try {
      jdbcIdo_.deleteObjects(al);

    } catch (Exception e) {
      logger.error(ExceptionHelper.getLocalizedMessage(e));
      fail(method + e.getMessage());
    }
    jdbcIdo_ = null;
  }
}