# Assignment-2
COP 4520


Problem 1:

In my approach to solving this problem, the goal was to create a solution in which the strategy used by the guests successfully has them, or one of them, notify the minotaur that 
everybody has gone through the maze without them breaking the rule of not communicating during the game. 
The solution I found goes like this: between the guests, one will be selected as the counter to keep count of how many guests have gone through the maze, while the rest of the guests 
will just worry about going into the maze without having to keep any sort of count.
As for what they do when they reach the end of the maze and they encounter the cupcake:
-When there is a cupcake on the plate, the counter will always eat the cupcake and will add 1 to their personal count.
-If there is not a cupcake on the plate, the counter will leave the plate empty and not ask for a replacement cupcake, the count does not change.
-When there is a cupcake on the plate, the regular guests will leave the cupcake and do nothing.
-If there is not a cupcake on the plate, the regular guests will ask for a replacement cupcake. They can only ask for a replacement once, after that one time, any other time that they 
encounter an empty plate they must leave the plate empty.

Once the counter reaches a count equal to the total number of guests, the counter will notify the minotaur and end the game.

To implement this solution into a program, I set the number of guests to 8 and initiate a counter thread, and then have a for-loop initiate the other concurrent threads. The counter 
thread will run while the count does not equal the total amount of guests. While running the counter will check the plate and do as the strategy suggests. The other guests will run 
until the counter reaches their necessary count and “notifies the minotaur”. They will check the plate as instructed in the solution.

As for the technical side, I used an atomic integer for the cupcake status to keep synchronization, and for checking said cupcake I used a synchronized lock to ensure that guests go 
through the maze one at a time. The other technique that I used was having the threads sleep for a random amount of time before checking the plate (going through the maze) to ensure a 
random order in which guests go into the maze.

To run this program through the command these are the steps (type these exactly in your command line): -javac Assignment1.java -java Assignment1


Problem 2:

Out of the three provided strategies, I chose this one: The third strategy would allow the quests to line in a queue. Every guest exiting the room was responsible for notifying the
guest standing in front of the queue that the showroom is available. Guests were allowed to queue multiple times. This strategy ensures that each guest books their place to see the 
vase, as each guest will book their place in the queue and be guaranteed the opportunity to see the vase. Waiting may still occur as we don’t know how much time a guest might take 
viewing the vase. It also ensures that only one guest enters the room to see the vase as the minotaur desired. So, overall, although it does not avoid waiting, it guarantees that each 
guest that wants to see the vase, will have their chance as long as they join the queue, while also ensuring that only one guest enters at a time.

As for the other two strategies, I found that they contain major flaws that make them inferior options to the one discussed in the prior paragraph. 

The first option: Any guest could stop by and check whether the showroom’s door is open at any time and try to enter the room. While this would allow the guests to roam around the 
castle and enjoy the party, this strategy may also cause large crowds of eager guests to gather around the door. A particular guest wanting to see the vase would also have no guarantee 
that she or he will be able to do so and when. This option fails miserably in that it breaks the rule desired by the minotaur in having only one guest enter the room, multiple people 
would be able to enter endangering the vase. 

The second option: The Minotaur’s second strategy allowed the guests to place a sign on the door indicating when the showroom is available. The sign would read 
“AVAILABLE” or “BUSY.” Every guest is responsible for setting the sign to “BUSY” when entering the showroom and back to “AVAILABLE” upon exit. That way guests would not bother trying 
to go to the showroom if it is not available. This option does not necessarily fail, but it is worse than the third option as this option might result in some guests never getting 
their chance at seeing the vase as they might be unlucky and see the busy sign each time they check the availability of the room. Waiting will still be involved, but overall, the major 
flaw is that it does not guarantee equal opportunity for all guests as some guests might repeat their viewing of the vase and that might result in some guests getting unlucky each time 
they want to see the vase and seeing that the sign says busy.

In my implementation of the third strategy, I used a lock-based queue of threads for 5 threads or guests. I had each thread be a “guest” thread and they would run each for a duration
of a while loop while a certain count was less than the amount of guests. I had each guest continually using enqueue when they weren’t in the line and dequeue only if they were at the 
front of the line. The enqueue function would add to the count each time a guest is enqueued.

To run this program through the command these are the steps (type these exactly in your command line): -javac Assignment2P2.java -java Assignment2P2
