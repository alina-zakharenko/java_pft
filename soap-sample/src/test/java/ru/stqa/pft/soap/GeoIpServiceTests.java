package ru.stqa.pft.soap;

import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp(){
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("93.100.46.23");
    assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>66</State></GeoIP>");
  }

//
//  public void myIp() {
//    String location = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("46.138.4.49");
//    System.out.println(location);
}
