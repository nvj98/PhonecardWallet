# PhonecardWallet
This program implement classes with inheritance, aggregation and composition, and works with polymorphic types.
This program uses a set of classes that represent phone cards for making cheap long distance calls. There are many kinds of phone cards, so we define the abstract class PhoneCard that collects common features among them. One specific kind of card is represented by the class SmartAmericas10Card, a subclass of  PhoneCard. It only allows calls to Canada, the USA, and Latin America. Another specific kind of card is represented by the class Global25Card: it allows calls to all countries.  These classes use another class CallZone that represents the different zones to which calls can be made. They also use a class Call that represents a call to a given number in a given call zone for a given number of minutes. There is also another class  PhoneCardWallet that represent a collection of phone cards.  
