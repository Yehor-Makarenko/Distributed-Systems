package Lab4.TaskA.Java.Phonebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Phonebook {  
  private static File dbNames = new File("Labs/src/Lab4/TaskA/resources/dbNames.properties");    
  private static File dbPhones = new File("Labs/src/Lab4/TaskA/resources/dbPhones.properties");    
  private static FileInputStream fis;
  private static FileOutputStream fos;
  private static ReadWriteLock rwLock = new ReentrantReadWriteLock();
  private static Lock readLock = rwLock.readLock();
  private static Lock writeLock = rwLock.writeLock();

  static public String getPhoneByName(String name) {
    Properties props = new Properties();
    String nameWithoutSpaces = name.replaceAll(" ", "_");
    String phone;

    try {
      fis = new FileInputStream(dbNames);
      props.load(fis);
      fis.close();
    } catch (IOException e) {      
      e.printStackTrace();
    }

    phone = props.getProperty(nameWithoutSpaces);
    return phone;        
  }

  static public String getNameByPhone(String phone) {    
    Properties props = new Properties();
    String name;

    try {
      fis = new FileInputStream(dbPhones);
      props.load(fis);
      fis.close();
    } catch (IOException e) {      
      e.printStackTrace();
    }

    name = props.getProperty(phone);
    return name;        
  }

  static public void addPerson(String name, String phone) {
    Properties props = new Properties();
    String nameWithoutSpaces = name.replaceAll(" ", "_");    

    try {
      fis = new FileInputStream(dbNames);
      props.load(fis);
      fis.close();
      props.setProperty(nameWithoutSpaces, phone);      
      fos = new FileOutputStream(dbNames);            
      props.store(fos, "");
      fos.close();     
      props.clear();  
      fis = new FileInputStream(dbPhones);
      props.load(fis);
      fis.close();    
      props.setProperty(phone, nameWithoutSpaces);
      fos = new FileOutputStream(dbPhones);            
      props.store(fos, "");
      fos.close();      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static public void removePersonByName(String name) {
    Properties props = new Properties();
    String nameWithoutSpaces = name.replaceAll(" ", "_");
    try {
      fis = new FileInputStream(dbNames);
      props.load(fis);
      fis.close();
      props.remove(nameWithoutSpaces);      
      fos = new FileOutputStream(dbNames);            
      props.store(fos, "");
      fos.close();      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static public String getRandName() {
    Properties props = new Properties();
    String name = "";

    try {
      fis = new FileInputStream(dbNames);
      props.load(fis);
      fis.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Enumeration<Object> names = props.keys();  
    for (int i = 0; i < (int)(props.size() * Math.random()); i++) {
      names.nextElement();
    }
    name = names.nextElement().toString().replaceAll("_", " ");
    return name;
  }

  static public String getRandPhone() {
    Properties props = new Properties();
    String phone = "";

    try {
      fis = new FileInputStream(dbPhones);
      props.load(fis);
      fis.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Enumeration<Object> phones = props.keys();  
    for (int i = 0; i < (int)(props.size() * Math.random()); i++) {
      phones.nextElement();
    }
    phone = phones.nextElement().toString();
    return phone;
  }

  static public boolean hasName(String name) {
    Properties props = new Properties();    

    try {
      fis = new FileInputStream(dbNames);
      props.load(fis);
      fis.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return props.containsKey(name);
  }
}