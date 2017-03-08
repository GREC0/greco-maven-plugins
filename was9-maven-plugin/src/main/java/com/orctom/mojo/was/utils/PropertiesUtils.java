package com.orctom.mojo.was.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesUtils
{
  public static Properties loadProperties(URL url)
  {
    try
    {
      return loadProperties(url.openStream());
    }
    catch (Exception e) {}
    return null;
  }
  
  public static Properties loadProperties(File file)
  {
    try
    {
      return loadProperties(new FileInputStream(file));
    }
    catch (Exception e) {}
    return null;
  }
  
  public static Properties loadProperties(InputStream is)
  {
    try
    {
      Properties properties = new Properties();
      if (null != is) {
        properties.load(is);
      }
      return properties;
    }
    catch (IOException e)
    {
      Properties localProperties1;
      return null;
    }
    finally
    {
      try
      {
        if (is != null) {
          is.close();
        }
      }
      catch (IOException e) {}
    }
  }
  
  public static Map<String, Properties> loadSectionedProperties(URL url)
  {
    return loadSectionedProperties(url, null);
  }
  
  public static Map<String, Properties> loadSectionedProperties(URL url, Properties defaultProps)
  {
    try
    {
      return loadSectionedProperties(url.openStream(), defaultProps);
    }
    catch (Exception e) {}
    return null;
  }
  
  public static Map<String, Properties> loadSectionedProperties(File file)
  {
    return loadSectionedProperties(file, null);
  }
  
  public static Map<String, Properties> loadSectionedProperties(File file, Properties defaultProps)
  {
    try
    {
      return loadSectionedProperties(new FileInputStream(file), defaultProps);
    }
    catch (Exception e) {}
    return null;
  }
  
  public static Map<String, Properties> loadSectionedProperties(InputStream is)
  {
    return loadSectionedProperties(is, null);
  }
  
  public static synchronized Map<String, Properties> loadSectionedProperties(InputStream is, Properties defaultProps)
  {
    try
    {
      SectionedProperties properties = new SectionedProperties(defaultProps);
      if (null != is) {
        properties.load(is);
      }
      return properties.getProperties();
    }
    catch (IOException e)
    {
      Map localMap;
      return null;
    }
    finally
    {
      try
      {
        if (is != null) {
          is.close();
        }
      }
      catch (IOException e) {}
    }
  }
  
  public static String resolve(String expression, Properties properties)
  {
    return resolve(expression, properties, "{{", "}}");
  }
  
  public static String resolve(String expression, Properties properties, String startSign, String endSign)
  {
    StringBuilder template = new StringBuilder(expression);
    for (Map.Entry<Object, Object> entry : properties.entrySet())
    {
      String pattern = startSign + entry.getKey() + endSign;
      String value = entry.getValue().toString();
      int start;
      while ((start = template.indexOf(pattern)) != -1) {
        template.replace(start, start + pattern.length(), value);
      }
    }
    return template.toString();
  }
  
  public static class SectionedProperties
    extends Properties
  {
    private static final long serialVersionUID = 1L;
    private Pattern sectionPattern = Pattern.compile("\\s*\\[([^]]*)\\]\\s*");
    private Map<String, Properties> properties = new HashMap();
    private Properties defaultProps = new Properties();
    private Properties readingSection = new Properties();
    public static final String DEFAULT_SECTION = "[DEFAULT]";
    
    public SectionedProperties() {}
    
    public SectionedProperties(Properties _props)
    {
      if ((null != _props) && (!_props.isEmpty())) {
        this.defaultProps = _props;
      }
    }
    
    public synchronized Object put(Object keyObj, Object valueObj)
    {
      String key = String.valueOf(keyObj);
      String value = String.valueOf(valueObj);
      Matcher sectionMatcher = this.sectionPattern.matcher(key);
      if (sectionMatcher.matches())
      {
        if ("[DEFAULT]".equals(key))
        {
          this.readingSection = this.defaultProps;
        }
        else
        {
          this.readingSection = new Properties();
          if ((null != this.defaultProps) && (!this.defaultProps.isEmpty())) {
            this.readingSection.putAll(this.defaultProps);
          }
          this.properties.put(sectionMatcher.replaceAll("$1"), this.readingSection);
        }
      }
      else {
        this.readingSection.put(key, value);
      }
      return valueObj;
    }
    
    public Map<String, Properties> getProperties()
    {
      return this.properties;
    }
  }
}
