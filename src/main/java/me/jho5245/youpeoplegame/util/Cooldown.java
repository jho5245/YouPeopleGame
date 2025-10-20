package me.jho5245.youpeoplegame.util;

public class Cooldown
{
	private final long max;

	private long current;

	public Cooldown(long max)
	{
		this.max = max;
		this.current = max;
	}

	public void tick()
	{
		if (this.current > 0)
			this.current--;
	}

	public long getMax()
	{
		return this.max;
	}

	public long getCurrent()
	{
		return this.current;
	}

	public boolean isOver()
	{
		return getCurrent() == 0;
	}
}
