# StudiesWebServer
A website in Spring Boot and MySQL made for a specific discord server purpose.

You can share access to this panel by giving the **TOKEN** away to your friends.

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

## Sessions

The panel uses **Redis** as the built-in database for storing
sessions in memory. 
They are stored in the cookie `JSESSIONID`, not the data, but the session ID. 
The data to that session is in memory.
Keeping in mind that the panel may not be used my many people, but only by few selected admins.

## TODO

- Add pagination for `/studies` and `/songs`.
  `?page=1` 10 items per page.