# Bloket
Bloket is an android call and text management application which intercepts incoming calls and text messages, allowing users to block them. It provides user the capability to add numbers to “Blacklist” from contacts list or by manually entering the number. Bloket can block a range of numbers, or numbers starting with certain first digits. In addition to these Bloket lets users create a group of contacts which can be blacklisted.
In case, user do not want to block calls from a certain number, adding it to the “Whitelist” allows calls form the whitelisted numbers. Callers from this list will never be rejected by the blocker.

# Weekly Updates

#### Week 01 - Jan 30, 2019

In the initial phase of the product development, our first challenge was to design the user interface. We sketched a few designs and chose to go with a tabular layout that hosts five main tab components, namely dialer, contacts list, call logs, blacklist and whitelist. 

On the android architecture side, we implemented one activity and multiple fragments structure. A challenge we faced was implementing FAB (Floating action button) which respects the design overhaul and goes along with the horizontal and vertical tab scrolling. This was not easy to achieve due to lack of native support from Android, we wrote our own FAB scrolling behavior class.

The first module we started working on was Dialer. Development started by implementing functionality for dialing, editing & deleting a number. Later we added T9 dialing support which filters and shows the contact names based on the number input. This was another challenging task and we had to work with regex and filters. This is still incomplete as we are willing to show all the numbers of a contact along with the display name. We also intend to work on highlighting the part of text in contact name and the matched numbers depending on the search query.

We also started working on contact list module which basically lists all the contacts on a device with numbers, grouped and sorted alphabetically. We implemented a search view to filter the contact list based on the search query. The floating action button in this module allow users to create a new contact. We will later implement a contact info page, which will host all information about the contact and will provide options to add/edit contact data. 

#### Week 02 - Feb 06, 2019

Earlier to fetch contacts with all phone numbers, we had to make (1+N) database queries. This lead to a noticeable performance issue when dealing with more than 1000 contacts. We have fixed the issue this week, now we can fetch the data in one query, however we get redundant data in the process. We fixed this issue with hashmap, but later chose to iterate the cursor and store numbers for each contact, which boosted the performance. We have tested with 20000 contacts and it works without any lag.

We worked on text highlighting for matched contacts and successfully completed it. This took some time to learn and get familiar with regex. Now the T9 search is extended to both contact names and all the numbers for each contacts.

We also improved permission handling where in some part of codes required a specific permission to do the task, but lets say if user revoked the granted permission at some time then it lead to a crash.

This week, we also started working on intercepting calls, one of our core module. We wrote a call interceptor , or a call broadcast listener as we say in Android, but that didn't work as expected. The latest changes to Android framework introduced in Android Pie does not let developers access the internal framework required for the blocking mechanism. We are still struggling to find a workaround. This has slowed down the pace of development and we hope to find a solution for this in coming weeks.
