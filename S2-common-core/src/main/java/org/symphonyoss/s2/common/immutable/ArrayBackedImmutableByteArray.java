/*
 *
 *
 * Copyright 2018 Symphony Communication Services, LLC.
 *
 * Licensed to The Symphony Software Foundation (SSF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The SSF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.symphonyoss.s2.common.immutable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.codec.binary.Base64;
import org.symphonyoss.s2.common.reader.ByteArrayReader;

@Immutable
class ArrayBackedImmutableByteArray extends ImmutableByteArray
{
  private final byte[] bytes_;
  private String       stringValue_;
  private String       base64UrlSafeValue_;
  private String       base64Value_;

  ArrayBackedImmutableByteArray(byte[] bytes)
  {
    bytes_ = bytes;
  }

  @Override
  public Reader createReader(Charset charset)
  {
    return new ByteArrayReader(bytes_, charset);
  }

  @Override
  public void write(OutputStream out) throws IOException
  {
    out.write(bytes_);
  }

  @Override
  public String toString()
  {
    if(stringValue_ == null)
      stringValue_ = new String(bytes_, StandardCharsets.UTF_8);
    
    return stringValue_;
  }
  
  @Override
  public String toBase64UrlSafeString()
  {
    if(base64UrlSafeValue_ == null)
      base64UrlSafeValue_ = Base64.encodeBase64URLSafeString(bytes_);
    
    return base64UrlSafeValue_;
  }
  
  @Override
  public String toBase64String()
  {
    if(base64Value_ == null)
      base64Value_ = Base64.encodeBase64String(bytes_);
    
    return base64Value_;
  }

  @Override
  public Iterator<Byte> iterator()
  {
    return new ByteIterator();
  }
  
  private class ByteIterator implements Iterator<Byte>
  {
    private int   index_ = 0;
    
    @Override
    public boolean hasNext()
    {
      return index_ < bytes_.length;
    }

    @Override
    public Byte next()
    {
      return bytes_[index_++];
    }
  }

  @Override
  public byte[] toByteArray()
  {
    return Arrays.copyOf(bytes_, bytes_.length);
  }
}
