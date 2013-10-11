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
package org.synchronoss.cpo.cassandra.meta;

import com.datastax.driver.core.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.synchronoss.cpo.CpoException;
import org.synchronoss.cpo.meta.AbstractCpoMetaAdapter;
import org.synchronoss.cpo.meta.DataTypeMapEntry;
import org.synchronoss.cpo.meta.DataTypeMapper;
import org.synchronoss.cpo.parser.BoundExpressionParser;
import org.synchronoss.cpo.parser.ExpressionParser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dberry
 * Date: 9/10/13
 * Time: 08:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class CassandraCpoMetaAdapter extends AbstractCpoMetaAdapter {
  private static final Logger logger = LoggerFactory.getLogger(CassandraCpoMetaAdapter.class);
  private static final DataTypeMapEntry<String> defaultDataTypeMapEntry = new DataTypeMapEntry<String>(DataType.Name.VARCHAR.ordinal(), DataType.Name.VARCHAR.toString(), String.class);
  private static final DataTypeMapper dataTypeMapper = initDataTypeMapper();

  @Override
  protected DataTypeMapper getDataTypeMapper() {
    return dataTypeMapper;
  }

  @Override
  public ExpressionParser getExpressionParser() throws CpoException {
    return new BoundExpressionParser();
  }

  private static DataTypeMapper initDataTypeMapper() {
    logger.debug("Initializing the DataMapper");
    DataTypeMapper dataTypeMapper = new DataTypeMapper(defaultDataTypeMapEntry);

    // CQL DataTypes
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<String>(DataType.Name.ASCII.ordinal(), DataType.Name.ASCII.toString(), String.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Long>(DataType.Name.BIGINT.ordinal(), DataType.Name.BIGINT.toString(), long.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<ByteBuffer>(DataType.Name.BLOB.ordinal(), DataType.Name.BLOB.toString(), ByteBuffer.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Boolean>(DataType.Name.BOOLEAN.ordinal(), DataType.Name.BOOLEAN.toString(), boolean.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Long>(DataType.Name.COUNTER.ordinal(), DataType.Name.COUNTER.toString(), long.class));
//    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Object>(DataType.Name.CUSTOM.ordinal(), DataType.Name.CUSTOM.toString(), Object.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<BigDecimal>(DataType.Name.DECIMAL.ordinal(), DataType.Name.DECIMAL.toString(), BigDecimal.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Double>(DataType.Name.DOUBLE.ordinal(), DataType.Name.DOUBLE.toString(), double.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Float>(DataType.Name.FLOAT.ordinal(), DataType.Name.FLOAT.toString(), float.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<InetAddress>(DataType.Name.INET.ordinal(), DataType.Name.INET.toString(), InetAddress.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Integer>(DataType.Name.INT.ordinal(), DataType.Name.INT.toString(), int.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<List>(DataType.Name.LIST.ordinal(), DataType.Name.LIST.toString(), List.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Map>(DataType.Name.MAP.ordinal(), DataType.Name.MAP.toString(), Map.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<Set>(DataType.Name.SET.ordinal(), DataType.Name.SET.toString(), Set.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<String>(DataType.Name.TEXT.ordinal(), DataType.Name.TEXT.toString(), String.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<String>(DataType.Name.TIMESTAMP.ordinal(), DataType.Name.TIMESTAMP.toString(), String.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<UUID>(DataType.Name.TIMEUUID.ordinal(), DataType.Name.TIMEUUID.toString(), UUID.class));
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<UUID>(DataType.Name.UUID.ordinal(), DataType.Name.UUID.toString(), UUID.class));
    dataTypeMapper.addDataTypeEntry(defaultDataTypeMapEntry);
    dataTypeMapper.addDataTypeEntry(new DataTypeMapEntry<BigInteger>(DataType.Name.VARINT.ordinal(), DataType.Name.ASCII.toString(), BigInteger.class));

    logger.debug("Returning the DataMapper");
    return dataTypeMapper;
  }
}
