package eecs2030.pe2;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that represents a smart phone card for the Americas. For
 * SmartAmericas10 phone cards, only calls to Canada, the USA, and Latin America
 * are allowed. For calls to Canada the cost per minute is $0.03, for calls to
 * the USA the cost per minute is $0.05, and for calls to Latin America the cost
 * per minute is $0.10. The initial balance on the card is $10.00. The weekly
 * fees are $0.30.
 * 
 * <p>
 * Each card also keeps track of the history of calls charged to the card.
 * The call history is List&lt;Call&gt;.  For example, a brand new card would have
 * as its call history the empty list []. If the card was then used to call
 * +14167362100 in Canada for 2 minutes, its call history would then become
 * [call to number +14167362100 in zone CANADA for 2 minutes].
 * If the card was then used to call +14152121000 in the USA for 2 minutes, its call history
 * would then become [call to number +14167362100 in zone CANADA for 2 minutes, call to number +14152121000 in zone USA for 2 minutes].
 * Every time a call is successfully charged to the card, it is added to the call history.
 * The relation between SmartAmericas10Card and its call history is composition.
 * 
 * @author EECS2030
 * 
 */
public class SmartAmericas10Card extends PhoneCard
{
	/**
	 * The initial balance for SmartAmericas10 cards.
	 */
	public static final double INITIAL_BALANCE = 10.0;
	
	/**
	 * The weekly fee for SmartAmericas10 cards.
	 */
	public static final double WEEKLY_FEES = 0.3;
	
	/**
	 * The cost per minute for calls to Canada for SmartAmericas10 cards.
	 */
	public static final double COST_PER_MIN_TO_CANADA = 0.03;
	
	/**
	 * The cost per minute for calls to the USA for SmartAmericas10 cards.
	 */
	public static final double COST_PER_MIN_TO_USA = 0.05;
	
	/**
	 * The cost per minute for calls to Latin America for SmartAmericas10 cards.
	 */
	public static final double COST_PER_MIN_TO_LATINAM = 0.1;

	private List<Call> callHistory;
	
	/**
	 * Create a SmartAmericas10Card phone card with the given number and password.
	 * Sets card's balance to INITIAL_BALANCE and its call history to the empty list.
	 * 
	 * @param number the card's number.
	 * @param password the card's password.
	 * @pre. number and password are positive.
	 */
	public SmartAmericas10Card(long number, int password)
	{
		super(number, password, SmartAmericas10Card.INITIAL_BALANCE);
		this.callHistory = new ArrayList<Call>();
	}

	/**
	 * Create a copy of the given SmartAmericas10Card.
	 * A deep copy is returned.
	 * 
	 * @param card the card to make a copy of.
	 * @pre. card is not null.
	 */
	public SmartAmericas10Card(SmartAmericas10Card card)
	{
		this(card.getNumber(), card.getPassword());
		this.callHistory = card.getCallHistory();  // rely on getCallHistory() for deep copy
	}

	/**
	 * Get the history of calls as a List.
	 * The client can modify the
	 * returned List without changing the state of the card.
	 * 
	 * @return a List containing the call history of the card.
	 */
	public List<Call> getCallHistory()
	{   // here we make a deep copy and return it
		List<Call> callList = new ArrayList<Call>();
		for(Call c : this.callHistory)
		{
			callList.add(new Call(c));
		}
		return callList;
	}
	
	/**
	 * Get the set of call zones that can be called on this phone card. For
	 * SmartAmericas10 phone cards, only calls to Canada, the USA, and Latin America
	 * are allowed.
	 * 
	 * @return the set of allowed call zones on the card.
	 */
	@Override
	public Set<CallZone> allowedZones()
	{
		Set<CallZone> allowedZonesSet = new HashSet<CallZone>();
		allowedZonesSet.add(CallZone.CANADA);
		allowedZonesSet.add(CallZone.USA);
		allowedZonesSet.add(CallZone.LATINAM);
		return allowedZonesSet;
	}

	/**
	 * Get the cost per minute of a call to the argument zone on this phone card.
	 * 
	 * @param zone the call zone to find the cost for.
	 * @return the cost per minute to call the given call zone.
	 * @pre. zone is not null and a call to zone is allowed for this card.
	 */
	@Override
	public double costPerMin(CallZone zone)
	{
		assert this.isAllowed(zone);
	    if(zone == CallZone.CANADA)
	    {
	    	return SmartAmericas10Card.COST_PER_MIN_TO_CANADA;
	    }
	    else if(zone == CallZone.USA)
	    {
	        return SmartAmericas10Card.COST_PER_MIN_TO_USA;
	    }
	    else
	    {
	    	assert (zone == CallZone.LATINAM);
	    	return SmartAmericas10Card.COST_PER_MIN_TO_LATINAM;
	    }
	}

	/**
	 * Deduct the appropriate weekly fees from the card's balance. If the
	 * balance is insufficient, the balance becomes 0.
	 * 
	 */
	@Override
	public void deductWeeklyFee()
	{
		this.setBalance(Math.max(0, this.getBalance() - SmartAmericas10Card.WEEKLY_FEES));
	}

	/**
	 * Check whether a call to the argument zone is allowed for this phone card.
	 * For SmartAmericas10 phone cards, only calls to Canada, the USA, and Latin America
	 * are allowed.
	 * 
	 * @param zone the call zone to check.
	 * @return true if the card supports the call zone; false otherwise.
	 * @pre. zone is not null.
	 */
	@Override
	public boolean isAllowed(CallZone zone)
	{
		return zone.equals(CallZone.CANADA) || zone.equals(CallZone.USA) || zone.equals(CallZone.LATINAM);
	}

	/**
	 * Charge the given call to this phone card.
	 * This method tries to charge the given call to the card.
	 * If the balance is sufficient to cover it, the call is charged and added to the call history, and
	 * the value true is returned.
	 * If the balance is insufficient, the balance and call history are left unchanged and false is returned.
	 * The client can later mutate the call without changing the state of the card and its call history.
	 * 
	 * @param call the call to charge.
	 * @return true if the balance was sufficient to pay for the call, and false otherwise.
	 * @pre. call is not null and its zone is allowed for this card.
	 */
	@Override 
	public boolean charge(Call call)
	{  
		boolean result = super.charge(call);
		if (result)
		{
			this.callHistory.add(new Call(call));  // we use Call's copy constructor 
			                               // to make a copy of the call and avoid privacy leaks
		}
		return result;
	}

	/**
	 * Compares the card with another object for equality. Two cards are equal
	 * if and only if their PhoneCard sub-objects are equal and their call
	 * histories are equal.
	 * 
	 * @param obj the object to compare with for equality.
	 * @return true if the card and object are equal; false otherwise.
	 * 
	 */
	@Override 
	public boolean equals(Object obj)
	{
		boolean eq = super.equals(obj);
		if(eq)
		{
			SmartAmericas10Card other = (SmartAmericas10Card) obj;
			if(!this.getCallHistory().equals(other.getCallHistory()))  // rely on List equals
			{                                                          // to compare call history
				eq = false;
			}
		}
		return eq;
	}
}
