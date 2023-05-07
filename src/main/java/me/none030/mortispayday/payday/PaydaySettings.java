package me.none030.mortispayday.payday;

import java.time.LocalDateTime;

public class PaydaySettings {

    private final String defaultPaydayId;
    private final RebuildMode warRebuildMode;
    private final long warRebuildDays;
    private final long productionPercentage;
    private final boolean nationRestrict;
    private final boolean townRestrict;
    private final boolean townConqueredRestrict;
    private final long conqueredRestrictDays;
    private final WarRestriction warRestriction;
    private final boolean capitalWarRestrict;
    private final boolean nationDemocracy;
    private final boolean townDemocracy;

    public PaydaySettings(String defaultPaydayId, RebuildMode warRebuildMode, long warRebuildDays, long productionPercentage, boolean nationRestrict, boolean townRestrict, boolean townConqueredRestrict, long conqueredRestrictDays, WarRestriction warRestriction, boolean capitalWarRestrict, boolean nationDemocracy, boolean townDemocracy) {
        this.defaultPaydayId = defaultPaydayId;
        this.warRebuildMode = warRebuildMode;
        this.warRebuildDays = warRebuildDays;
        this.productionPercentage = productionPercentage;
        this.nationRestrict = nationRestrict;
        this.townRestrict = townRestrict;
        this.townConqueredRestrict = townConqueredRestrict;
        this.conqueredRestrictDays = conqueredRestrictDays;
        this.warRestriction = warRestriction;
        this.capitalWarRestrict = capitalWarRestrict;
        this.nationDemocracy = nationDemocracy;
        this.townDemocracy = townDemocracy;
    }

    public LocalDateTime getRebuildTime() {
        if (warRebuildDays <= 0) {
            return LocalDateTime.now();
        }
        return LocalDateTime.now().plusDays(warRebuildDays);
    }

    public boolean isConqueredDays(int days) {
        if (conqueredRestrictDays == -1) {
            return true;
        }
        return days >= conqueredRestrictDays;
    }

    public String getDefaultPaydayId() {
        return defaultPaydayId;
    }

    public RebuildMode getWarRebuildMode() {
        return warRebuildMode;
    }

    public long getWarRebuildDays() {
        return warRebuildDays;
    }

    public long getProductionPercentage() {
        return productionPercentage;
    }

    public boolean isNationRestrict() {
        return nationRestrict;
    }

    public boolean isTownRestrict() {
        return townRestrict;
    }

    public boolean isTownConqueredRestrict() {
        return townConqueredRestrict;
    }

    public long getConqueredRestrictDays() {
        return conqueredRestrictDays;
    }

    public WarRestriction getWarRestriction() {
        return warRestriction;
    }

    public boolean isCapitalWarRestrict() {
        return capitalWarRestrict;
    }

    public boolean isNationDemocracy() {
        return nationDemocracy;
    }

    public boolean isTownDemocracy() {
        return townDemocracy;
    }
}
