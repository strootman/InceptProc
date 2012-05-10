package com.tyrellcorp.shared;

import java.io.Serializable;
import java.util.Date;

public class Replicant implements Serializable
{
	private static final long serialVersionUID = 8760082195358485314L;
	
	private String model;
	private String name;
	private String identifier;
	/**
	 * The date a replicant is activated, its birth date.
	 */
	private Date inceptDate;

	public Replicant() { }
	
	public Replicant(String model, String name, String id, Date incept) {
		this.model = model;
		this.name = name;
		this.identifier = id;
		this.inceptDate = incept;
	}

	public Replicant(String model, String name, String id) {
		this.model = model;
		this.name = name;
		this.identifier = id;
		this.inceptDate = new Date(System.currentTimeMillis() + 4 * 3600000L);
	}
	
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getIdentifier()
	{
		return identifier;
	}
	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}
	public Date getInceptDate()
	{
		return inceptDate;
	}
	public void setInceptDate(Date inceptDate)
	{
		this.inceptDate = inceptDate;
	}	

	/**
	 * We should note this is not sufficient for real-world 
	 * scenarios - but this is totally cool in the world of 
	 * Los Angeles 2019 
	 */
	@Override
	public boolean equals(Object obj) {
		if((obj == null) || !(obj instanceof Replicant))
			return false;
		
		Replicant rhs = (Replicant)obj;
		return this.identifier.equals(rhs.identifier);
	}

	@Override
	public int hashCode()
	{
		return this.identifier.hashCode();
	}
	
	@Override
	public String toString()
	{
		return model + " " + identifier + " " + name + " " + inceptDate.toString();
	}
}
