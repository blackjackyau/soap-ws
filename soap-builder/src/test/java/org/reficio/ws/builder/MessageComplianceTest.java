/**
 * Copyright (c) 2012-2013 Reficio (TM) - Reestablish your software!. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.reficio.ws.builder;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reficio.ws.SoapContext;
import org.reficio.ws.common.ResourceUtils;
import org.reficio.ws.legacy.SoapLegacyFacade;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

/**
 * User: Tom Bujok (tom.bujok@gmail.com)
 * Date: 17/10/11
 * Time: Soap11:06 AM
 */
public class MessageComplianceTest {

    private final static Logger log = Logger.getLogger(MessageComplianceTest.class);

    @BeforeClass
    public static void setUpClass() {
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setNormalizeWhitespace(true);
        XMLUnit.setXSLTVersion("1.0");
    }

    public static String getContent(String folderPath, String fileName) {
        URL fileUrl = ResourceUtils.getResourceWithAbsolutePackagePath(folderPath, fileName);
        File file = null;
        try {
            file = new File(fileUrl.toURI());
        } catch (URISyntaxException e) {
            file = new File(fileUrl.getPath());
        }
        try {
            return FileUtils.readFileToString(file).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SoapContext context = SoapContext.builder()
            .exampleContent(false)
            .typeComments(false)
            .alwaysBuildHeaders(false)
            .valueComments(false)
            .buildOptional(true)
            .build();

    @Test
    public void testEmptyFaultSoap11() throws IOException, SAXException {
        String emptyFaultSoap11 = SoapLegacyFacade.buildEmptyFault(SoapLegacyFacade.Soap.SOAP_1_1, context);
        log.info("\n" + emptyFaultSoap11);
        String expectedMsg = getContent("messages", "EmptyFault11.xml");
        compareResult(expectedMsg, emptyFaultSoap11);
    }

    @Test
    public void testEmptyFaultSoap12() throws IOException, SAXException {
        String emptyFaultSoap12 = SoapLegacyFacade.buildEmptyFault(SoapLegacyFacade.Soap.SOAP_1_2, context);
        log.info("\n" + emptyFaultSoap12);
        String expectedMsg = getContent("messages", "EmptyFault12.xml");
        compareResult(expectedMsg, emptyFaultSoap12);
    }

    @Test
    public void testFaultSoap11() throws IOException, SAXException {
        String faultSoap11 = SoapLegacyFacade.buildFault(SoapLegacyFacade.Soap.SOAP_1_1, "VersionMismatch", "Fault Message", context);
        log.info("\n" + faultSoap11);
        String expectedMsg = getContent("messages", "FaultVersionMismatch11.xml");
        compareResult(expectedMsg, faultSoap11);
    }

    @Test
    public void testFaultSoap12() throws IOException, SAXException {
        String faultSoap12 = SoapLegacyFacade.buildFault(SoapLegacyFacade.Soap.SOAP_1_2, "VersionMismatch", "Fault Message", context);
        log.info("\n" + faultSoap12);
        String expectedMsg = getContent("messages", "FaultVersionMismatch12.xml");
        compareResult(expectedMsg, faultSoap12);
    }

    @Test
    public void testEmptyMessageSoap11() throws IOException, SAXException {
        String emptyMessageSoap11 = SoapLegacyFacade.buildEmptyMessage(SoapLegacyFacade.Soap.SOAP_1_1, context);
        log.info("\n" + emptyMessageSoap11);
        String expectedMsg = getContent("messages", "EmptyMessage11.xml");
        compareResult(expectedMsg, emptyMessageSoap11);
    }

    @Test
    public void testEmptyMessageSoap12() throws IOException, SAXException {
        String emptyMessageSoap12 = SoapLegacyFacade.buildEmptyMessage(SoapLegacyFacade.Soap.SOAP_1_2, context);
        log.info("\n" + emptyMessageSoap12);
        String expectedMsg = getContent("messages", "EmptyMessage12.xml");
        compareResult(expectedMsg, emptyMessageSoap12);
    }

    private void compareResult(String expected, String actual) throws IOException, SAXException {
        Diff diff = XMLUnit.compareXML(expected, actual);
        assertTrue(diff.toString(), diff.similar());
        assertTrue(diff.toString(), diff.identical());
    }
}
