package CSE360Project;

// Stores data about an effort that was carried out.
public class Effort {
	// How long the effort took, in seconds.
	private float duration;
	
	public Effort(float duration) {
		this.duration = duration;
	}
	
	public float GetDuration() {
		return duration;
	}
	
	public void SetDuration(float duration) {
		this.duration = duration;
	}
}
