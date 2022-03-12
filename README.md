# BillShare
Share payment with roommates!

This app aims to help user manage bills and debts.

[Front-end application](https://github.com/darlingof02/billshare_frontend) is built in ReactJS

# Goal Features & User Case
## Features completed
User:
1.	User login/logout 
2.	User Authorization using JWT
3.	Edit User profile

Bill:
1.	Manage bills/indebts:
    a.	Create bill
    b.	Manage bills/indebts such as decline/accept/pay/confirm

## Features in following iterations
* settle indebts with multiple users
* social media part, creating groups and chatrooms
* create group shared bill
* settle indebts in group
* Statistics


## User Case Diagram
![4071644805775_ pic](https://user-images.githubusercontent.com/41298248/157998842-bdf5f066-d72d-4cee-9e1f-aed899bcc2f5.jpg)



# Entities/Relations
![4061644800638_ pic](https://user-images.githubusercontent.com/41298248/157998338-f1aecb04-d0b2-4e07-9b11-65c333e6f155.jpg)
## User
User entity records users’ information. Every user registered in this app will have a unique record.

In realization, we use email as user’s ID.

## Bill 
record information related to shared bills. Every bill has a record in Bill.

* Bill entity has several basic fields to describe one bill. They are amount, type and comment.
* Bill entity has 2 timestamp field: 
    * Create Time: the time when the bill was created.
    * Due Time: the time when debts should be settled.
* It also has a status indicating whether it’s settled or not.

## InDebt
It’s a relation which keeps track of debtors in each shared bill. Each debtor in each bill will correspond an indebt record.
* This relation also has amount field indicating the amount of payment for the debtor
* 2 timestamp field show 
    * when it’s accepted by the debtor
    * when it’s paid
* The status is an important field in indebt relation, as it indicates the status of each indebt record
    * -1 means the debtor declined the payment request
    * 0 means that the bill is created and the indebt is generated but debtor hasn’t claimed to accept
    * 1 means that the indebt has been accepted by debtor
    * 2 means that the debtor has paid his part of shared bill
    * 3 means that the bill owner has confirmed payment from the debtor and this debt is settled and archived


# Realization (Controller level)
## BillController 
BillController is responsible for events related to bill such as bill/indebt management and response bill/indebt data to front-end.

## UserController 
UserController handles request related to users such as edit user profile and send user data to front-end;

## JWTController
JWTController deals with Authenticating and Authorizing users.


# Completed features:















