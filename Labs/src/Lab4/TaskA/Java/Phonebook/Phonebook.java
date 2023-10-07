package Lab4.TaskA.Java.Phonebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Phonebook {  
  private static File dbNames = new File("Labs/src/Lab4/TaskA/resources/dbNames.properties");    
  private static File dbPhones = new File("Labs/src/Lab4/TaskA/resources/dbPhones.properties");      
  private static ReadWriteLock rwLock = new ReentrantReadWriteLock();
  private static Lock readLock = rwLock.readLock();
  private static Lock writeLock = rwLock.writeLock();  
  private static Condition noPeopleCondition = new ReentrantLock().newCondition();

  public static HashMap<String, String> removedPeople = new HashMap<>();

  static public String getPhoneByName(String name) {
    readLock.lock();
    Properties props = new Properties();
    String nameWithoutSpaces = name.replaceAll(" ", "_");
    String phone;

    try {      
      props.load(new FileInputStream(dbNames));      
    } catch (IOException e) {      
      e.printStackTrace();
    }

    phone = props.getProperty(nameWithoutSpaces);
    readLock.unlock();
    return phone;        
  }

  static public String getNameByPhone(String phone) {    
    readLock.lock();
    Properties props = new Properties();
    String name;

    try {      
      props.load(new FileInputStream(dbPhones));      
    } catch (IOException e) {      
      e.printStackTrace();
    }

    name = props.getProperty(phone);
    readLock.unlock();
    return name;        
  }

  static private void addPerson(String name, String phone) {
    Properties props = new Properties();
    String nameWithoutSpaces = name.replaceAll(" ", "_");    

    try {      
      props.load(new FileInputStream(dbNames));      
      props.setProperty(nameWithoutSpaces, phone);            
      props.store(new FileOutputStream(dbNames), "");      
      props.clear();        
      props.load(new FileInputStream(dbPhones));      
      props.setProperty(phone, nameWithoutSpaces);      
      props.store(new FileOutputStream(dbPhones), "");      
    } catch (IOException e) {
      e.printStackTrace();
    }

    noPeopleCondition.signalAll();
    removedPeople.remove(name);
  }

  static private void removePerson(String name, String phone) {
    Properties props = new Properties();
    String nameWithoutSpaces = name.replaceAll(" ", "_");    

    try {      
      props.load(new FileInputStream(dbNames));  
      props.remove(nameWithoutSpaces);            
      props.store(new FileOutputStream(dbNames), "");     
      props.clear();
      props.load(new FileInputStream(dbPhones));  
      props.remove(phone);            
      props.store(new FileOutputStream(dbPhones), "");      
    } catch (IOException e) {
      e.printStackTrace();
    }

    removedPeople.put(name, phone);
  }

  static public String getRandName() {
    readLock.lock();
    Properties props = new Properties();
    String name = "";

    try {      
      props.load(new FileInputStream(dbNames));      
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (props.keySet().size() == 0) {
      try {
        noPeopleCondition.await();
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }

      try {      
        props.load(new FileInputStream(dbNames));      
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Enumeration<Object> names = props.keys();  
    for (int i = 0; i < (int)(props.size() * Math.random()); i++) {
      names.nextElement();
    }
    name = names.nextElement().toString().replaceAll("_", " ");
    readLock.unlock();
    return name;
  }

  static public String getRandPhone() {
    readLock.lock();
    Properties props = new Properties();
    String phone = "";

    try {      
      props.load(new FileInputStream(dbPhones));      
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (props.keySet().size() == 0) {
      try {
        noPeopleCondition.await();
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }

      try {      
        props.load(new FileInputStream(dbNames));      
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Enumeration<Object> phones = props.keys();  
    for (int i = 0; i < (int)(props.size() * Math.random()); i++) {
      phones.nextElement();
    }
    phone = phones.nextElement().toString();
    readLock.unlock();
    return phone;
  }

  public static void removeRandPerson() {
    writeLock.lock();
    String name = getRandName();
    String phone = getPhoneByName(name);

    removePerson(name, phone);
    System.out.println("Removed: " + name + " " + phone);
    writeLock.unlock();
  }

  public static void addRandPerson() {
    writeLock.lock();
    String name = Phonebook.removedPeople.keySet().toArray()[0].toString();
    String phone = Phonebook.removedPeople.get(name);

    addPerson(name, phone);
    System.out.println("Added: " + name + " " + phone);
    writeLock.unlock();
  }
}