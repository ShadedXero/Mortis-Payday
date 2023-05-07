package me.none030.mortispayday.data;

import com.palmergames.bukkit.towny.object.Town;

import java.time.LocalDateTime;

public class PaydayData extends Data {

    private final String paydayKey = "MortisPaydayId";
    private final String resourcesKey = "MortisPaydayResources";
    private final String timerKey = "MortisPaydayTimer";
    private final String rebuildTimerKey = "MortisPaydayRebuild";

    public PaydayData(Town town) {
        super(town);
    }

    public void create(String id, String resources, LocalDateTime time) {
        setId(id);
        setResources(resources);
        setTimer(time);
    }

    public void setId(String id) {
        set(paydayKey, id);
    }

    public String getId() {
        return get(paydayKey);
    }

    public void setResources(String resources) {
        set(resourcesKey, resources);
    }

    public String getResources() {
        return get(resourcesKey);
    }

    public void setTimer(LocalDateTime timer) {
        if (timer == null) {
            set(timerKey, null);
            return;
        }
        set(timerKey, timer.toString());
    }

    public LocalDateTime getTimer() {
        String value = get(timerKey);
        if (value == null) {
            return null;
        }
        return LocalDateTime.parse(value);
    }

    public void setRebuildTimer(LocalDateTime rebuildTimer) {
        if (rebuildTimer == null) {
            set(rebuildTimerKey, null);
            return;
        }
        set(rebuildTimerKey, rebuildTimer.toString());
    }

    public LocalDateTime getRebuildTimer() {
        String value = get(rebuildTimerKey);
        if (value == null) {
            return null;
        }
        return LocalDateTime.parse(value);
    }
}
