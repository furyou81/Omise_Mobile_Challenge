# Omise_Challenge

## SET UP FOR THE NODE JS SERVER:

- first run: `npm install` in the terminal from the root folder to install the dependencies

- if you are using a mobile phone (not the emulator), please make sur to be connected on the same internet network on your mobile phone and on the server

- run the command `ifconfig` in your terminal to check your ip adress

- in the index.js file put your IP address in `const hostname = YOUR IP ADDRESS`

- set your omise skey and pkey in environment variables to be accessed from the node js server

  - `export OMISE_SKEY=skey_your_omise_key`

  - `export OMISE_PKEY=pkey_your_omise_key`

- to start the server, run the command `node index.js`
- if an error occured, please make sure that you put the right IP address




## SET UP FOR THE ANDROID APP

- open the project (OmiseChallenge folder) in Android Studio
- go to `build menu -> rebuild project`

- in MainActivity
  - please fill your IP adress and you pkey_test in those 2 variables
    - static final String URL = "http://" + `"YOUR_IP_ADDRESS"` + ":3000";
    - static final String OMISE_PKEY = `"YOUR_PKEY_TEST"`;
# Pictures of the project

## Icon
![Alt text](/pictures/1.jpg?raw=true "Icon")

## Main menu checking connectivity
![Alt text](/pictures/2_0.jpg?raw=true "Main menu checking connectivity")

## Main menu
![Alt text](/pictures/2.jpg?raw=true "Main menu")

## Charity list
![Alt text](/pictures/3.jpg?raw=true "Charity list")

## Donation screen
![Alt text](/pictures/4.jpg?raw=true "Donation screen")

## Credit card screen
![Alt text](/pictures/5.jpg?raw=true "Credit card screen")

## Donation screen
![Alt text](/pictures/6.jpg?raw=true "Donation screen")

## Transaction waiting screen
![Alt text](/pictures/7.jpg?raw=true "Transaction waiting screen")

## Transaction status screen
![Alt text](/pictures/8.jpg?raw=true "Transaction status screen")
