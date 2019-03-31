<h1>Uni-Directional Voice Notes Microservice</h1>

<h2>Summary</h2>
This service is responsible for sending the voice notes from the captain to the riders waiting for this bus.
Main Features of the service are: 

<h6>For a customer</h6>

1. Allow them to subscribe to a “journey” in order to receive voice-notes from the captain running this
journey.
2. Receive multiple voice notes from the same captain as long as the rider is still waiting for
the bus.
3. Confirm if the customer received or lstened to the voice note.

<h6>As a captain</h6>

1. Allow the captain to send voice notes to all customers who have booked this journey but currently waiting in
the next bus stops.
2. The ability to know how many customers received the voice note and how many of them
have listened to the voice note.
3. Change the status of a rider to indicate that they have now boarded and should stop receiving voice notes.

The APIs should mainly be called from the driver app (or the journey management service) and the customer app. 

<h2>System Details and Flow</h2>

1. The API to **"create a journey"** should be called at the beginning of any journey. It creates a journey entity in the service ready to handle voice notes and riders. 
2. Whenever a rider registers to a journey, the API **"subscribe to journey"** is called to create a rider entity attached to the journey in question with a status *"WAITING"*.
3. The API **"Send a voice note"** is called by the captain to send a voice note to all the subscribed still waiting riders. The voice note should be uploaded to an AWS S3 prior to calling this API and its link should be sent in the API to be sent to the customer apps for them to download it.
5. A voice note entity is then created in the service with a generated external id.
4. The service will filter the subscribed riders by their status, whether they are waiting or already on board. If they are waiting, the vocie note will be sent to them.
6. For sending the vocie note to the customers, a customer app API should be available to receive the voice note id and link with a specific customer id. A gateway is used to call the customer app API.
7. When a customer receives a voice note, the customer app should call the API **"update voice note status"** with that customer id and voice note id to update with status *"RECEIVED"*. The service will then save that status for this vocie note and customer. 
8. When a customer receives a voice note, the customer app should call the API **"update voice note status"** with that customer id and voice note id to update with status *"LISTENED"*. The service will then save that status for this vocie note and customer. 
9. Once a customer boards the bus, the captain app should call the API **"update rider status"** with a customer id, journey tracking is and rider status *"ON_BOARD"*. This will prevent the rider from receiving anymore status.

<h2>Databse Architecture</h2>

There are 4 tables in the DB (please refer to the diagram for field details): 

* **Journey**
* **Rider**
* **Voice Note**
* **Voice Note Rider Log**

* Each Journey had multiple riders and multiple voice notes (one to many relationship). 
* Each voice note has multiple voice note rider logs (one to many relationship)
* Each rider has multiple voice note rider logs (one to many relationship)

![Database architecture](https://github.com/MariamMSeleem/careem-voice-note-service-task/blob/development/voice-note-service-DB.png)

<h2>Technology justification</h2>


<h2>Additional notes</h2>
Redis can be used to store the list of riders who received or listened to the voice note. It will be quickly accsessed and if we don't need to store that info, it will be deleted automatically after a configurale period of time as it is volatile.






