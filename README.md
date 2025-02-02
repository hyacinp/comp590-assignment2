# Explaining My Design Rationale For Solving the Dining Philosophers Problem

## Features and Structure Used

Note: the while loop in the Philosopher class is given a duration of 10 seconds to make sure that the program produces
enough output in order to be able to make conclusions, while ensuring that the while loop does not go on forever.

### Philosophers

* The Philosopher class is used to instantiate each philosopher around the dining table
* This class implements the Runnable interface to allow each philosopher to be able to run as a thread.
* The philosopherNumber is the number associated with each philosopher so that they can be easily identifiable
* Each philosopher has a right and left Fork created through the Fork class, since they need both forks to eat
* The Philosopher class also has a dining table of type Semaphore; this is done to limit the number of philosophers 
that can be at the table at once
  * **The use of Semaphore here is done to ensure that only 4 out of 5 philosophers can be at the dining table
  at once. The philosopher must `acquire` a "permit" into the dining table. After eating, the philosopher `releases`
  its permit, which then allows the waiting philosopher to sit down at the table and get their chance to eat.**

It is important that only 4 out of 5 philosophers can only be at the table at a time, because this situations would 
allow at least one philosopher to be able to eat at a time. With 5 philosophers, there would be a **deadlock** because
all philosophers would pick up their left fork, but be stuck waiting for their respective right forks in order to eat,
because all the other forks are already picked up by their neighbors. With 4 philosophers only at the table, then one fork
is always free. Therefore, this use of Semaphore to limit the number of people that can be at the dining table at once,
prevents reaching a **deadlock**.

### Forks

* Each fork of a philosopher is an instance of the Fork class
* Each fork is also given an id/number through the forkId field
  * This is used to specify which fork is being picked up or put down by each philosopher
* The Fork class has a field named lock which is a ReentrantLock
  * This was created in order to enforce mutual exclusion to prevent race conditions between the philosophers when picking up and putting down forks
#### Methods

The fork class contains two methods: 

* `pickUpFork()` represents picking up a specific fork
* `putDownFork()` represents putting down a specific fork

The `pickUpFork()` method locks the lock while the putDownFork method unlocks the lock. This means the lock is held throughout eating.
The philosopher only puts down the forks after eating, so the `putDownFork()` method unlocks the lock so that the surrounding philosophers
can use the respective forks they need and share with the current philosopher with the forks. **This similarly helps ensure mutual exclusion
and prevent race conditions as it ensures that no two forks can be picked up simultaneously.**

### Spaghetti

* The "Spaghetti" is represented by the `eat()` method within the Philosopher class. Eating is simply represented by this method
by printing out that the philosopher is eating (the spaghetti). This method has the philosopher sleep for a brief period in order to
simulate a more realistic situation (people don't just instantly transition between two different states)

### Thinking

* Similar to the `eat()` method within the Philosopher class, the `think()` method prints out that the philosopher is thinking
to represent their thinking phase, followed by having the philosopher "sleep" for a brief duration. 

### Life Cycle

Each philosopher that is instantiated goes through the lifecycle of waiting to enter the dining table. Once they have acquired "permission" to enter
the table, given that there is less than 5 people at the table, they enter the thinking phase as represented by the `think()` method of the
Philosopher class. 

After thinking, each philosopher picks up their respective forks which is locked from being picked up by another philosopher by the `pickUpFork()` method.
Once both forks are picked up, then the philosopher enters their eating phase which is induced by the `eat()` method. Then the philosopher indicates that 
they have finished eating by putting down BOTH of their respective left and right forks which is done through the `putDownFork()` method. The `putDownFork()` method
also unlocks each fork after being put down in order for neighboring philosophers to be able to pick up their forks, and prevent starvation.

### Other Ways Deadlock is Prevented

This solution was made so that every philosopher picks up their left fork first then the right fork second, except for the last philosopher.
This reduces the chances of a deadlock because it breaks this cyclic waiting condition.

### Preventing Starvation

Starvation is prevented through the use of a Semaphore dining table and the Reentrant Lock. By locking a fork when picking it up, it allows the
philosopher picking it up to have the chance to eat, but also having the `putDownFork()` method unlock the fork being put down after being used, ensures
that other philosophers that need to use the same fork can use it to eat thus preventing starvation. Additionally, the use of Semaphore ensures that once
a philosopher has eaten/gone through their life cycle, they can be released from the table, so that another philosopher that has been waiting can
acquire a spot within the dining table to eat, which also prevents starvation.

### Are Deadlocks and Starvation still Possible?

**Deadlocks**

I think deadlocks are very unlikely to occur due to the preventative measures taken by having the last philosopher pick up their first fork from the
side opposite of what every other philosopher picked up first, as well as through the use of Semaphore. In the very rare case that Philosopher 0, 1, 2 and 3
all pick up their left fork first right after one another, then they would all be stuck waiting to pick up their each respective right fork. This produces deadlock,
and has the last philosopher just waiting to enter the table, causing starvation. However, this is a very unlikely scenario since the scheduling of the threads
must align perfectly for this to occur. Thus, the features and structure I used works to avoid deadlocks and starvation for the most part.

**Starvation**

I think starvation is also unlikely due to the measures taken as stated above, but it is possible for the scheduler to allow other philosophers to keep
entering the dining table. We can never really know which philosopher the scheduler will schedule into **acquiring** the entrance into the dining table, so
the scheduling may not be exactly fair, because a philosopher may have to wait awhile to enter the dining table when another philosopher has reentered the table
multiple times.
