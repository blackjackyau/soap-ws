/**
 * Copyright (c) 2012-2013 Reficio (TM) - Reestablish your software!. All Rights Reserved.
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.reficio.ws.legacy;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.reficio.ws.SoapBuilderException;


/**
 * String implementation for SchemaLoader.
 */
public class StringSchemaLoader implements SchemaLoader, DefinitionLoader {
    private final String baseURI;
    private final String wsdlString;

    public StringSchemaLoader(String baseURI, String wsdlString) {
        this.baseURI = baseURI;
        this.wsdlString = wsdlString;
    }

    public XmlObject loadXmlObject(String string1, XmlOptions options) throws Exception {
        return XmlUtils.createXmlObject(wsdlString, options);
    }

    public String getBaseURI() {
        return baseURI;
    }

    public void setProgressInfo(String info) {
        throw new SoapBuilderException("Not Implemented");
    }

    public boolean isAborted() {
        throw new SoapBuilderException("Not Implemented");
    }

    public boolean abort() {
        throw new SoapBuilderException("Not Implemented");
    }

    public void setNewBaseURI(String uri) {
        throw new SoapBuilderException("Not Implemented");
    }

    public String getFirstNewURI() {
        throw new SoapBuilderException("Not Implemented");
    }
}
