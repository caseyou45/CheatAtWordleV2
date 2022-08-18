# CheatAtWordleV2

This program is Java Swing application designed to make the game of Wordle as easy as possible without just looking up the answer online. 
If you think about Wordle, what makes it difficult is 1) you don't know every word that can be played, 2) several words can be played on any given 
turn, and 3) it's difficult to tell which word is the "better" one to play when you have multiple options. 

This application tries to solve those problems by 1) knowing all the words Wordle can play, 2) presenting all the words that can be played, and 3) 
allowing the user to arrange those possible words. 

Here is a sample playthrough: 

Play One 
![one](https://user-images.githubusercontent.com/59487509/185440698-b1e93399-1cc7-4366-8851-7bd350476b38.png)

Play Two 
![two](https://user-images.githubusercontent.com/59487509/185440725-bc4ef1fe-123e-4115-85c5-9fac0ab66896.png)

Play Three 
![three](https://user-images.githubusercontent.com/59487509/185440756-f0cd000f-aae2-4a2e-ac1d-1c666a599ca3.png)


With each play, the words remaining and bar graph will change.  

The list of words remaining decreases as the application performs basic analysis of the words remaining. As an example, for the first word 
“IRATE,” the application removed all words with an I, R, and E. It also removed all words that didn’t have an A in the third position (1-indexed).
And finally, it removed all words that did not have a T at all or did have a T in the fourth position.  

As this occurs, the application counts the number of letters remaining in all words and then displays them as a bar graph. 

Finally, the list of words remaining can be ordered by different methodologies.  

The previous words tab above opens a page to allow you to view or edit the list of words Wordle has already used.  
