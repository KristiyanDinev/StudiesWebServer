# StudiesWebServer
A website in Spring Boot and MySQL made for a specific discord server purpose.

You can share access to this panel by giving the **TOKEN** away to your friends.

To set that token. Set an environment variable called `WEBAUTH_TOKEN` to that token.

Another option is `WEB_LOGS` in your environment variables:

- `true` -> It will log actions like **Adding**, **Editing**, **Deleting** in the `logs/` folder.


- `false` -> No logs.

## Studies

There a page for uploading a study in **PDF** format.
You can also view and manage the studies.

You can:
- **Delete** - Remove the study from the disk

- **Download** - Redirects the user to download that study

- **Upload** - Upload your new studies to the server. *(You can give them a new name if you want).*

## Songs

A page for you to upload music. So bots or other websites can use it.
Like online audio players or personal audio playlists.

You can:
- **Delete** - Remove the song from the disk and database

- **Download** - Automatically triggers a download

- **Upload** - Upload your new songs to the server. 
  You can give the song categories, so you can have a database with songs
  and their categories, mainly for sorting purposes and custom playlists.
  *(You can also give them a new name if you want).*

- **Edit** - You can modify the categories of that specific song.

## Sermons

*The same as songs*

## Sessions

The panel uses **Redis** as the built-in database for storing
sessions in memory. 
They are stored in the cookie `JSESSIONID`, not the data, but the session ID. 
The data to that session is in memory.
Keeping in mind that the panel may not be used my many people, but only by few selected admins.

Run **Redis** only
```bash
docker run --name redis-db -e REDIS_ARGS="--requirepass mypassword" -itd -p 6379:6379 redis
```

Run **Redis** + **Redis Insight WebServer**
```bash
docker run --name redis-db -itd -e REDIS_ARGS="--requirepass mypassword" -p 6379:6379 -p 8001:8001 redis/redis-stack
```

Run **MySQL** server

```bash
docker run -itd --name mysql-db -d mysql -e MYSQL_DATABASE=TheDatabaseLibDB -e MYSQL_USER=TheUser -e MYSQL_PASSWORD=YourPasswor
```

### How to run

#### Step **1** get the **DatabaseLib** 

*(ONLY DO THIS IF IT IS YOUR FIRST TIME OR YOU ARE UPDATING THE **DATABASE_LIB**)*

1. Go to the **DatabaseLib** private repo. **FORK** it and go to your account repos.


2. Go to **Actions**, which is at the top a bit right of the screen.


3. Locate **Java CI with Gradle.** and *Click* it.


4. Then locate **Run workflow** and *Click* it. Then **wait** for it to complete. You would know if it is completed if it has a **green checkmark**.


5. *Click* on the name **Java CI with Gradle.** and scroll down until you find a section called **Artifacts**.


6. Download the **DatabaseLib** artifact by *Clicking* on the name **DatabaseLib** or the download button on the far right.

#### Step 2 FORK the Study Website (basically making a independent copy of the website on your account)

1. Go to `https://github.com/KristiyanDinev/StudiesWebServer` and *Click* on the **Fork** button located at the top right.


2. Then *Click* on the green button **Create fork**.

#### Step 3 put the **DatabaseLib** file in the FORKED Study Website

1. Go to your **FORKED** website, which is located in your personal account repos.


2. Go to **Code** located at the top far left.


3. Go to the `libs/` folder and there should be a guide on how to upload it.

---

**If the guide is not there here is a copy:**

**How to upload the Database Lib here**

1. Locate the **Add file** button at top right and *Click* it.


2. Choose the option **Upload files**


3. Upload the **database_lib-.....-all.jar**


4. *Click* on the green button **Commit changes**


**How to delete the Database Lib**

1. **Open** the **database_lib-......-all.jar**


2. *Click* on the tree dots on the top right


3. Scroll down a bit and *Click* on **Delete file**, then on the green button **Commit changes...** and then again.
---


#### Step 4 download the website from the FORK you made

1. Go to **Actions**, which is at the top a bit right of the screen.


2. Locate **Java CI with Gradle.** and *Click* it.


3. Then locate **Run workflow** and *Click* it. Then **wait** for it to complete. You would know if it is completed if it has a **green checkmark**.


4. *Click* on the name **Java CI with Gradle.** and scroll down until you find a section called **Artifacts**.


5. Download the **Website** artifact by *Clicking* on the name **Website** or the download button on the far right.

#### Step 5 run

```bash
java -Xms600m -Xmx1024m -jar StudiesWebServer-[version].jar --spring.profiles.active=prod
```

- Use MAX **1 GB** of RAM

- Use Initial / MIN **600 MB** of RAM

- Use Spring `application-prod.yml` profile to run the website

### Images

<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_1.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_2.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_3.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_4.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_5.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_7.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_8.png">
<img src="https://raw.githubusercontent.com/KristiyanDinev/StudiesWebServer/refs/heads/main/img_9.png">
