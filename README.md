# Personal Finance Application

## CPSC 210 Term Project - Phillip Dumitru


Building a user friendly personal finance application in order to keep track of spending and savings.

### What will this application do?
- This application will serve as a place for someone to manage their finances. It is supposed to keep a history of 
purchases and automatically deduct that money from the balance that was set when the person started using the app.
It will also serve as a place to track savings, and keep a history of incoming money, and always update the displayed
balance accordingly. 

- This application is to be used by anyone who is looking for a simplified way to keep track of their finances.
Its purpose an offline application, in order to maintain maximum security, and all the files will be stored locally in
the user's directory.
 
- This project is of personal interest to me, because I keep track of my personal finances on an excel spreadsheet, and
I wanted to create an application that can implement similar features with a more friendly user interface. I want it to 
be simple, so anyone can use it. I would like to simplify some of the features I have on my personal excel spreadsheet 
(graphs, automatically adjusting balance, etc.) which require a certain proficiency in excel to implement, in order to 
make it easier and quicker for someone to manage their finances.

I look forward to creating an application which is intuitive and easy to use, and hopefully simplify
money management for someone with a busy schedule like myself.

### User Stories
- As a user, I want to be able to create an account that has a balance and transactions.
- As a user, I want to be able to set an initial balance, or modify my balance.
- As a user, I want to be able to add an outgoing transaction, containing of:
    - A value of how much money it involves
    - A category (Personal and Household, Professional Services, Retail and Grocery, Transportation, 
    Hotels Entertainment and Recreation, Restaruants, Home and Office, Health and Education) for the transaction
    - A type (card, electronic, cash)
- As a user, I want to be able to add an incoming transaction, containing of:
    - A value of how much money it involves
    - A category (paycheck, gift, award, rebate)
    - A type (cheque, electronic, cash)
- As a user, I want to tie a transaction to an account
- As a user, I want my account balance to reflect the changes that are caused by an incoming or outgoing transaction
- As a user, I want to be able to view information about transactions tied to an account:
    - A list of all transactions
    - A list of incoming transactions
    - A list of outgoing transactions
- As a user, I want to be able to set saving goals for a certain account

##### Phase 2 User Stories Additions
- As a user, I want to be able to save my Account information when I exit the application.
- As a user, I want to be able to load in my information at startup
- As a user, I want to be able to clear all my information before exiting the application.

##### Phase 3 User Stories Additions
- As a user, I want to be able to add transactions to my account through my GUI.
- As a user, I want to be able to view the transactions that I have added to an account on my GUI.
- As a user, I want to be able to save and load my application through my GUI.
- As a user, I want to see a bar graph of my saving history.

##### Phase 4 Task 2
Used a class hierarchy for implementing the different accounts of the project, with Account as the
superclass, and SavingAccount and ChequingAccount as my two subclasses. In account there is an abstract method
called "transact", which is overrided in both ChequingAccount and SavingAccount.

##### Phase 4 Task 3
Looking at my UML diagram, my implementation of my classes in my model package is pretty clean, and most of the 
classes follow the SOLID principles. One change that I would make, would be to make sure that SavingAccount and 
ChequingAccount both follow LSP, as currently there are one or two methods which violate.
 
 The issues started with my UI. 
My approach for the UI was to make a new window for every possible view that the user would interact with. This proved 
to be a messy approach, and although there was an attempt at retaining a class hierarchy, it quickly digressed into a bit
of a mess. I tried to implement controllers for the accounts, but it didn't always worked, and I ended up with a mix of
Viewer classes that would modify the account as well as the controller, which made the code very complicated. In the future,
I would refactor the project to make sure that all modifications to the user account go through the controller, and the viewers
just show the information and collect any information that needs to be passed on to the controller. I would also make more
use of listeners, as I think that would have been the proper way to implement my saving goals as well as my different transaction
views.

Because this was my first time designing a UI, it turned out to be sub-optimally designed. Because a window was generated every
time there was a user interaction with the application, the app can sometimes feel clunky to use. It would prove worthwhile 
to go back and re design the UI using fewer windows, and more panel switching using a cardlayout to improve the user experience. This 
would also solve my coupling issues, which led to a lot of broken code anytime edits would be made to the UI.

 


