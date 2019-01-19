import telnetlib
import requests

def send_to_server(c, sensor_info):
#this function send the POST request to AWS server(in the below url) where payload is the information about the slots
        url = "http://ec2-13-232-39-50.ap-south-1.compute.amazonaws.com/php/Api.php?apicall=update_slot_info";
        data = {"zone_id" : "", "slot_id" : "", "is_available" : 0}

        for key in sensor_info:
                data["zone_id"] = key;
                for slot_id in sensor_info[key]:
                        data["slot_id"] = slot_id;
                        data["is_available"] = sensor_info[key][slot_id];
                        r = c.post(url, data);

def main():
        #this is the host IP manually assigned to the ethernet shield connected to Arduino
        host="169.254.112.64"
        #connecting to Arduino via telnet
        tn = telnetlib.Telnet(host)
        tn.write("Trying to telnet...\n")
        sim = tn.read_until("Done",20)
        print sim
        #Initialising all the parking slots to be empty
        #Dict format is Builing(bgl16), Slot(A1), State(0/1)
        sensor_info = {"bgl16": {"A1": 0, "A2": 0} , "bgl17": {"A1": 0, "A2": 0}}
        print sensor_info
        prev_ch = []
        #to establish HTTP session with the server
        with requests.session() as c:
                while 1:
                        ch=tn.read_until("Done").splitlines()
                        #to send the update only if the state of the slot changes, so that no. of messages sent to server is reduced
                        if ch == prev_ch:
                                continue
                        prev_ch = ch
                        for line in ch:
                                if line.find("=") != -1:
                                        words = line.split()
                                        sensor_info[words[0]][words[1]] = 1-int(words[3])
                                        print sensor_info
                        print ch
                        send_to_server(c, sensor_info);
        return

if __name__ == '__main__':
        main()
