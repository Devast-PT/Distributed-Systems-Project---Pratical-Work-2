import requests
import json as JSONICO
import os
import jwt
import datetime
#from kafka import KafkaConsumer

class menu:

    def __init__(self, url):
        self._isLogged = False
        self._keepRunning = True
        self.url = url
        self.start
        self.accesstoken = ""
        self.refreshtoken = ""
        self.secretKey = "VFUTIILXDe4eTjsqyF8LnvQIvCdepIR0GjoCoQ02xVeF2ru668IJrTSZFAGv5OR"
        self.TOPIC_NAME = 'NewArtists'
        
        

    def start(self):
        while(self._keepRunning):
            if not self._isLogged:
                self.registerLogIn()
            else:
                self.menu()

    def registerLogIn(self):
        print(f"Please choose a option:")
        print(f"1. Register")
        print(f"2. Log In")
        print(f"99. Exit App")

        option = input("Please enter option: ")

        match option:
            case "1":
                self.username = input("Please enter a username (min 6 characteres): ")
                self.password = input("Please enter a password (min 6 characteres): ")
                self.email = input("Please enter a email: ")
                r = requests.post(
                    url = self.url + "/auth/register",
                    headers = {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data = JSONICO.dumps({
                        "username": self.username,
                        "password": self.password,
                        "email": self.email
                    })
                )
                if r.status_code == 200:
                    print(r.content)
                else:
                    print(f"Err try again -  {r.status_code}")
                throwaway = input("enter anything to continue....") 
            case "2":
                self.username = input("Please enter a username (min 6 characteres): ")
                self.password = input("Please enter a password (min 6 characteres): ")
                r = requests.post(
                    url = self.url + "/auth/login",
                    headers = {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data = JSONICO.dumps({
                        "username": self.username,
                        "password": self.password
                    })
                )
                if r.status_code == 200:
                    json = r.json()
                    self.accesstoken = json.get("accessToken")
                    self.refreshtoken = json.get("refreshToken")
                    self._isLogged = True

                    decoded_token = jwt.decode(self.accesstoken, self.secretKey, algorithms=["HS384"])
                    self._role = decoded_token.get('role')
                    self._id = decoded_token.get('id')

                else:
                    print(f"Err try again")
                throwaway = input("enter anything to continue....") 
            case "99":
                self._keepRunning = False
        os.system('cls')

                    

    def menu(self):
        while(self._isLogged):
            #consumer = KafkaConsumer(self.TOPIC_NAME)
            #consumer.
            print("welcome")
            print("Choose an option:")
            print("1. Register Artist or add register a show by an artist")
            print("2. List Artists by location")
            print("3. List Artists by art type")
            print("4. List previous shows of an artist")
            print("5. List next shows of an artists")
            print("6. Send Donation to an artist")
            print("7. List donations by an artist")
            print("8. Give an artist a rating")
            print("9. See the rating of an artist")
            print("10. Register with you email/phone to receive new updates")

            if self._role == 'ADMIN':
                print("11. Give an user ADMIN permissions")
                print("12. List artist by state")
                print("13. Aprove a previously not aproved artist")
                print("14. Consult/Change an Artist informaton")
            print("99. Exit App")

            option = input("Choose your number: ")
            os.system('cls')

            match option:
                case '1':
                    self.registerArtist()
                case '2':
                    self.artistByLocation()
                case '3':
                    self.artistByArt()
                case '4':
                    self.artistPreviousShows()
                case '5':
                    self.artistNextShows()
                case '6':
                    self.sendArtistDonation()
                case '7':
                    self.listArtistDonation()
                case '8':
                    self.giveArtistRating()
                case '9':
                    self.seeArtistRating()
                case '10':
                    self.registerForLive()
                case '11':
                    self.givePerms()
                case '12':
                    self.seeAllArtist()
                case '13':
                    self.aproveArtist()
                case '14':
                    self.changeArtist()
                case '99':
                    self._isLogged = False
                    self._keepRunning = False
                
    def registerArtist(self):
        print("Please register your artist or Add a location to one:")
        name = input("Name: ")
        art = input("Art: ")
        lat = input("Latitude of performance :")
        log = input("Longitude of performance :")
        user_date_input = input("Digite a data no formato YYYY-MM-DD: ")
        
        try:
            # Tenta converter a entrada do usuário em um objeto datetime
            user_date = datetime.datetime.strptime(user_date_input, "%Y-%m-%d")
                      
            
        except ValueError:
            print("Formato de data inválido. Use o formato YYYY-MM-DD.")
        else:
            # Formata a data para o formato ISO 8601
            formatted_date = user_date.isoformat()
            
            print(f"Data inserida: {formatted_date}")
            
            data = JSONICO.dumps({
                    "id" : self._id,
                    "name": name,
                    "art": art,
                    "lat": lat,
                    "log": log,
                    "date": formatted_date
                })
            
            print(data)
            
            r = requests.post(
                self.url + "/artist/register/",
                headers ={
                    'Content-Type': 'application/json; charset=utf-8',
                    'Authorization': self.accesstoken
                },
                data = data
            )
            if r.status_code == 200:
                print(r.content)

            else:
                print(f"Something went wrong {r.status_code}")
            throwaway = input("enter anything to continue....")
            os.system('cls')




    def artistByLocation(self):
        print("Please enter the location you want to check:")
        lat = input("Latitude of performance :")
        log = input("Longitude of performance :")
    
        data = JSONICO.dumps({
                "lat": lat,
                "log": log
            })
        
        url = "http://localhost:8083/api/v1/artist/byLocation"
        
        print(data)
        
        r = requests.post(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            },
            data = data
        )
        if r.status_code == 200:
            print(r.content)

        else:
            print(f"Something went wrong {r.status_code}")
        throwaway = input("enter anything to continue....")
        os.system('cls')

    def artistByArt(self):
        print("Please enter the art you want to check:")
        art = input("Art: ")
        
        url = "http://localhost:8083/api/v1/artist/byArt/" + art
        
        r = requests.get(
            url,
            headers ={
                'Authorization': self.accesstoken
            }
        )

        if r.status_code == 200:
            print(r.content)       
            throwaway = input("enter anything to continue....")
        else:
            print(f"Something went wrong  {r.status_code}")
            throwaway = input("enter anything to continue....") 

    def artistPreviousShows(self):
        print("Please enter the name")
        name = input("Name: ")
        

        current_date = datetime.datetime.now()
        formatted_date_str = current_date.isoformat()
            
        url = "http://localhost:8083/api/v1/artist/previousdate/"
        
        data = JSONICO.dumps({
                "name": name,
                "date": formatted_date_str
            })
            
        print(data)
            
        r = requests.post(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            },
            data = data
        )
        if r.status_code == 200:
            print(r.content)

        else:
            print(f"Something went wrong {r.status_code}")
        throwaway = input("enter anything to continue....")
        os.system('cls')


    def artistNextShows(self):
        print("Please enter the name")
        name = input("Name: ")
        current_date = datetime.datetime.now()
        formatted_date_str = current_date.isoformat()
        url = "http://localhost:8083/api/v1/artist/nextDate/"
        data = JSONICO.dumps({
                "name": name,
                "date": formatted_date_str
            })
        print(data)
        r = requests.post(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            },
            data = data
        )
        if r.status_code == 200:
            print(r.content)
        else:
            print(f"Something went wrong {r.status_code}")
        throwaway = input("enter anything to continue....")
        os.system('cls')



    def sendArtistDonation(self):
        print("Please enter the artist id you want to donate and the amount:")
        id = input("Artist Id:")
        amount = input("Donation:")
        url = "http://localhost:8083/api/v1/donation/register/" + self._id + "/" + id + "/" + amount

        r = requests.get(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            },
        )

        if r.status_code == 200:
            print(r.content)       
            throwaway = input("enter anything to continue....")
        else:
            print(f"Something went wrong  {r.status_code}")
            throwaway = input("enter anything to continue....")  

    def listArtistDonation(self):
        print("Please enter the artist id you want to see the donations:")
        id = input("Artist Id:")
        url = "http://localhost:8083/api/v1/donation/" + id
        r = requests.get(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            },
        )
        if r.status_code == 200:
            print(r.content)       
            throwaway = input("enter anything to continue....")
        else:
            print(f"Something went wrong  {r.status_code}")
            throwaway = input("enter anything to continue....")  

            
    def giveArtistRating(self):
        print("Please enter the artist name you want to rate:")
        name = input("Artist Name: ")
        rating = input("Give a rate from 0 to 10: ")
        rating = int(rating)
        print(rating)
        if rating < 0 or rating > 10:
            print("Sorry rating need to be between 0 and 10")
        else :
            url = "http://localhost:8083/api/v1/rating/register/" + str(self._id) + "/" + name + "/" + str(rating)
            r = requests.get(
                url,
                headers ={
                    'Content-Type': 'application/json; charset=utf-8',
                    'Authorization': self.accesstoken
                },
            )
            if r.status_code == 200:
                print(f"{r.content}")
            else:
                print(f"Something went wrong  {r.status_code}")
        throwaway = input("enter anything to continue....") 
    

    def seeArtistRating(self):
        print("Please enter the artist name you want see the rate:")
        name = input("Artist Name: ")
        url = "http://localhost:8083/api/v1/rating/getRatings/" + name
        r = requests.get(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            },
        )
        if r.status_code == 200:
            print(f"{r.content}")
        else:
            print(f"Something went wrong  {r.status_code}")
        throwaway = input("enter anything to continue....") 

    def registerForLive(self):
        print("Please enter email you want to register for pub:")
        email = input("Email:")
        r = requests.post(
            self.url + "/rating/register",
            headers ={
                'Authorization': self.accessToken
            },
            data = {
                "email": email
            }
        )
        if r.status_code == 200:
            json = r.json()
            print(json)            
            throwaway = input("enter anything to continue....")
        else:
            print(f"Something went wrong  {r.status_code}")
            throwaway = input("enter anything to continue....") 

    def givePerms(self):
        print("Please enter the name of the user:")
        username = input("username: ")
        url = "http://localhost:8083/api/v1/auth/turnAdmin/" + username

        r = requests.get(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            }
        )
        if r.status_code == 200:
            print(r.content)
        else:
            print(f"Something went wrong  {r.status_code}")
        throwaway = input("enter anything to continue....") 
        
    def seeAllArtist(self):
        print("Do you want to see all artist waiting aprovement?")
        read = input("Type 1 for yes, anything else will skip:  ")
        if read == "1":
            url = "http://localhost:8083/api/v1/artist/seeall/false"
            r = requests.get(
                url,
                headers ={
                    'Content-Type': 'application/json; charset=utf-8',
                    'Authorization': self.accesstoken
                }
            )
            if r.status_code == 200:
                print(r.content)
            else:
                print(f"Something went wrong  {r.status_code}")
        read = 0
        read = input("Type 1 for yes, anything else will skip")
        if read == "1":
            url = "http://localhost:8083/api/v1/artist/seeall/true"
            r = requests.get(
                url,
                headers ={
                    'Content-Type': 'application/json; charset=utf-8',
                    'Authorization': self.accesstoken
                }
            )
            if r.status_code == 200:
                print(r.content)
            else:
                print(f"Something went wrong  {r.status_code}")
        throwaway = input("enter anything to continue....") 
        
        
    def aproveArtist(self):
        print("Choose the artist you want to aprove")
        read = input("Artist Id: ")
        url = "http://localhost:8083/api/v1/artist/aprove/" + read
        r = requests.get(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            }
        )
        if r.status_code == 200:
            print(r.content)
        else:
            print(f"Something went wrong  {r.status_code}")
        throwaway = input("enter anything to continue....") 

    def changeArtist(self):
        print("Insert the data to change the artist")
        id = input("Artist Id: ")
        name = input("if you want to change the name insert the new name: ")
        art = input("if you want to change the art insert the new art: ")
        data = JSONICO.dumps({
            'id': str(id),
            'name': name,
            'art': art
        })
        print(data)
        url = "http://localhost:8083/api/v1/artist/change/"
        r = requests.post(
            url,
            headers ={
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': self.accesstoken
            },
            data=data
        )
        if r.status_code == 200:
            print(r.content)
        else:
            print(f"Something went wrong  {r.status_code}")
        throwaway = input("enter anything to continue....") 





    
    