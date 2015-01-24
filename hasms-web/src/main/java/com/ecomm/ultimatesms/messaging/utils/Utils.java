package com.ecomm.ultimatesms.messaging.utils;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class Utils
{
  private static final String DTD_FILENAME = "pap_1.0.dtd";
  private static final String WAP_DTD_RESOURCE_URL = "-//WAPFORUM//DTD PAP 1.0//EN";
  private static Map<String, String> mappingMap = getMapping();

  public static boolean checkNonEmptyStringAttribute(String attributeValue)
  {
    return (attributeValue != null) && (!attributeValue.trim().equals(""));
  }

  public static String convertTextForGSMEncodingURLEncoded(String text)
    throws Exception
  {
    StringBuffer convertedText = new StringBuffer();

    for (int i = 0; i < text.length(); i++)
    {
      char charToCheck = text.charAt(i);

      StringBuffer utfCharHex = charToHexString(charToCheck);

      String utfConvertedChar = mappingMap.get(utfCharHex.toString());

      if (utfConvertedChar != null)
        convertedText.append(utfConvertedChar);
      else {
        convertedText.append(charToCheck);
      }
    }

    return URLEncoder.encode(convertedText.toString(), "UTF-8");
  }

  public static String convertTextForUTFEncodingURLEncoded(String text, String encoding)
    throws Exception
  {
    StringBuffer convertedText = new StringBuffer();

    if (encoding.equals("UTF-8")) {
      text = new String(text.getBytes(encoding));
    }
    for (int i = 0; i < text.length(); i++)
    {
      StringBuffer utfCharHex = charToHexString(text.charAt(i));

      utfCharHex.insert(0, "%");
      utfCharHex.insert(3, "%");

      convertedText.append(utfCharHex);
    }

    return convertedText.toString();
  }

  private static StringBuffer charToHexString(char utfChar)
  {
    StringBuffer utfCharHex = new StringBuffer(Integer.toHexString(utfChar).toUpperCase());

    while (utfCharHex.length() < 4) {
      utfCharHex.insert(0, "0");
    }
    return utfCharHex;
  }

  public static byte[][] serializeMessage(Externalizable message) throws Exception
  {
    ByteArrayOutputStream out = null;
    ObjectOutputStream out1 = null;
    byte[][] res = new byte[2][];
    try
    {
      out = new ByteArrayOutputStream();
      out1 = new ObjectOutputStream(out);

      message.writeExternal(out1);
      out1.flush();
      out.flush();

      res[0] = message.getClass().getName().getBytes();
      res[1] = out.toByteArray();

      out1.close();
      out.close();
    }
    catch (Exception ex) {
      throw ex;
    } finally {
      try {
        if (out != null)
          out.close();
        if (out1 != null)
          out1.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return res;
  }

  public static Externalizable deserializeMessage(byte[][] message) throws Exception {
    ByteArrayInputStream in = null;
    ObjectInputStream in1 = null;
    String messageClassName = null;
    Externalizable res = null;
    try
    {
      messageClassName = new String(message[0]);
      byte[] messageObject = message[1];

      in = new ByteArrayInputStream(messageObject);
      in1 = new ObjectInputStream(in);

      Class messageClass = Class.forName(messageClassName);
      res = (Externalizable)messageClass.newInstance();
      res.readExternal(in1);

      in1.close();
      in.close();
    }
    catch (Exception ex) {
      throw ex;
    } finally {
      try {
        if (in != null)
          in.close();
        if (in1 != null)
          in1.close();
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    return res;
  }

  private static Map<String, String> getMapping()
  {
    Map<String, String> mappingMap = new HashMap<String, String>();

    mappingMap.put("0391", "A");
    mappingMap.put("0386", "A");
    mappingMap.put("03B1", "A");
    mappingMap.put("03AC", "A");
    mappingMap.put("0392", "B");
    mappingMap.put("03B2", "B");
    mappingMap.put("03B3", "Γ");
    mappingMap.put("03B4", "Δ");
    mappingMap.put("0395", "E");
    mappingMap.put("0388", "E");
    mappingMap.put("03B5", "E");
    mappingMap.put("03AD", "E");
    mappingMap.put("0396", "Z");
    mappingMap.put("03B6", "Z");
    mappingMap.put("0397", "H");
    mappingMap.put("0389", "H");
    mappingMap.put("03B7", "H");
    mappingMap.put("03AE", "H");
    mappingMap.put("03B8", "Θ");
    mappingMap.put("0399", "I");
    mappingMap.put("038A", "I");
    mappingMap.put("03AA", "I");
    mappingMap.put("03B9", "I");
    mappingMap.put("03AF", "I");
    mappingMap.put("03CA", "I");
    mappingMap.put("0390", "I");
    mappingMap.put("039A", "K");
    mappingMap.put("03BA", "K");
    mappingMap.put("03BB", "Λ");
    mappingMap.put("039C", "M");
    mappingMap.put("03BC", "M");
    mappingMap.put("039D", "N");
    mappingMap.put("03BD", "N");
    mappingMap.put("03BE", "Ξ");
    mappingMap.put("039F", "O");
    mappingMap.put("03BF", "O");
    mappingMap.put("038C", "O");
    mappingMap.put("03CC", "O");
    mappingMap.put("03C0", "Π");
    mappingMap.put("03A1", "P");
    mappingMap.put("03C1", "P");
    mappingMap.put("03C2", "Σ");
    mappingMap.put("03C3", "Σ");
    mappingMap.put("03A4", "T");
    mappingMap.put("03C4", "T");
    mappingMap.put("03A5", "Y");
    mappingMap.put("038E", "Y");
    mappingMap.put("03AB", "Y");
    mappingMap.put("03C5", "Y");
    mappingMap.put("03CD", "Y");
    mappingMap.put("03CB", "Y");
    mappingMap.put("03B0", "Y");
    mappingMap.put("03C6", "Φ");
    mappingMap.put("03A7", "X");
    mappingMap.put("03C7", "X");
    mappingMap.put("03C8", "Ψ");
    mappingMap.put("03C9", "Ω");
    mappingMap.put("03CE", "Ώ");

    return mappingMap;
  }

  public static String parseWAPPushResponseForResultCode(String response)
    throws Exception
  {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();

    XMLReader saxReader = saxParser.getXMLReader();

    saxReader.setFeature("http://xml.org/sax/features/validation", true);

    saxReader.setEntityResolver(new EntityResolver() {
      public InputSource resolveEntity(String publicId, String systemId) {
        if (publicId.equals("-//WAPFORUM//DTD PAP 1.0//EN")) {
          InputStream in = getClass().getClassLoader().getResourceAsStream("pap_1.0.dtd");
          return new InputSource(in);
        }
        return null;
      }
    });
    PAPResponseHandler papResponseHandler = new PAPResponseHandler();
    saxReader.setContentHandler(papResponseHandler);

    saxReader.parse(new InputSource(new ByteArrayInputStream(response.getBytes("UTF-8"))));

    return papResponseHandler.getResultCode();
  }

  private static class PAPResponseHandler extends DefaultHandler
  {
    String resultCode = null;

    public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException
    {
      if (qName.equals("response-result")) {
        this.resultCode = attributes.getValue("code");
      }
      super.startElement(uri, localName, qName, attributes);
    }

    public String getResultCode() {
      return this.resultCode;
    }
  }
}
