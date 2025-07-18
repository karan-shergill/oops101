// UML: https://tinyurl.com/28s35day

package state;

// State Interface
interface LightState {
    void switchLight(TrafficLight trafficLight);
}

// Concrete State: RedLightState
class RedLightState implements LightState {
    @Override
    public void switchLight(TrafficLight trafficLight) {
        System.out.println("Switching to Green");
        trafficLight.setState(new GreenLightState());
    }
}

// Concrete State: GreenLightState
class GreenLightState implements LightState {
    @Override
    public void switchLight(TrafficLight trafficLight) {
        System.out.println("Switching to Yellow");
        trafficLight.setState(new YellowLightState());
    }
}

// Concrete State: YellowLightState
class YellowLightState implements LightState {
    @Override
    public void switchLight(TrafficLight trafficLight) {
        System.out.println("Switching to Red");
        trafficLight.setState(new RedLightState());
    }
}

// Context: TrafficLight
class TrafficLight {
    private LightState currentState;

    public TrafficLight() {
        // Initial state
        currentState = new RedLightState();
    }

    public void setState(LightState state) {
        currentState = state;
    }

    public void switchLight() {
        currentState.switchLight(this);
    }
}


public class StateDemo1 {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();

        // Switching states
        trafficLight.switchLight(); // Switching to Green
        trafficLight.switchLight(); // Switching to Yellow
        trafficLight.switchLight(); // Switching to Red
        trafficLight.switchLight(); // Switching to Green (cycle repeats)
    }
}