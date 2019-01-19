#include <SPI.h>
#include <Ethernet.h>

//manually assigning MAC address to Arduino Ethernet Shield
byte mac [] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x04
};

//manually configuring IP address to Arduino Ethernet Shield. 
//Note that this has to match with the IP configuration of the host machine to which the Arduino is connected to.
//Alternatively this can be done using DHCP also.
IPAddress ip(169, 254, 112, 64);
IPAddress gateway(169, 254, 112, 1);
IPAddress subnet(255, 255, 0, 0);

//Telnet port number = 23
EthernetServer server(23);
boolean gotmsg = false;
//Naming convention: bxsy means slot number y in building number x
int b1s1 = 1;
int b1s2 = 1;
int b2s1 = 1;
int b2s2 = 1;
int count = 0;

//Initialisation
void setup() 
{
  Serial.begin(9600);
  while(!Serial){
    ;
  }
  Ethernet.begin(mac, ip, gateway, subnet);
  Serial.print("My IP address: ");
  ip = Ethernet.localIP();
  for (byte currentOctet = 0; currentOctet <4; currentOctet++) {
    Serial.print(ip[currentOctet], DEC);
    Serial.print(".");
  }
  Serial.println();
  server.begin();
  //The sensors are connected to pins 2,3,8 and 9
  pinMode(8,INPUT);
  pinMode(9,INPUT);
  pinMode(2,INPUT);
  pinMode(3,INPUT);
}

void loop() {
  EthernetClient client = server.available();
  if(client){
    if(!gotmsg){
      Serial.println("We have a new client");
      client.println("Hello, client\nDone");
      gotmsg = true;
    }
  
  //below code segment is used to send the data via telnet whenever a parking slot's state changes
  //A parking slot's state is considered to be valid when it remains stable (doesn't toggle) for 4 consecutive poll intervals
  //One poll interval is 500ms
  //The above caution is taken to avoid erroneous state changes that might be observed when some obstacle (say a person) passes over the sensor in a parking slot
  
  int b1s1_now = 1;
  int b1s2_now = 1;
  int b2s1_now = 1;
  int b2s2_now = 1;
  for (int i=0; i<4; i++){
    int b1s1_temp = digitalRead(8);
    int b1s2_temp = digitalRead(9);
    int b2s1_temp = digitalRead(2);
    int b2s2_temp = digitalRead(3);
    b1s1_now = b1s1_temp && b1s1_now;
    b1s2_now = b1s2_temp && b1s2_now;
    b2s1_now = b2s1_temp && b2s1_now;
    b2s2_now = b2s2_temp && b2s2_now;
    delay(500);
  }
  if (b1s1 != b1s1_now){
    client.print("bgl16 A1 = ");
    client.println(b1s1_now);
  }
  if (b1s2 != b1s2_now){
    client.print("bgl16 A2 = ");
    client.println(b1s2_now);
  }
  if (b2s1 != b2s1_now){
    client.print("bgl17 A1 = ");
    client.println(b2s1_now);
  }
  if (b2s2 != b2s2_now){
    client.print("bgl17 A2 = ");
    client.println(b2s2_now);
  }
  client.println("Done");
  b1s1 = b1s1_now;
  b1s2 = b1s2_now;
  b2s1 = b2s1_now;
  b2s2 = b2s2_now;
  count++;

  //below code is used to send the state of all the slots every minute
  //this is done as a fault tolerance mechanism to overwrite any stale or erroneous entry in the database
  if (count == 30) {
    count = 0;
    client.println("One minute update:");
    client.print("bgl16 A1 = ");
    client.println(b1s1);
    client.print("bgl16 A2 = ");
    client.println(b1s2);
    client.print("bgl17 A1 = ");
    client.println(b2s1);
    client.print("bgl17 A2 = ");
    client.println(b2s2);    
    client.println("Done");
    }
  }
}
