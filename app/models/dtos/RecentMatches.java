package models.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "match_id",
        "player_slot",
        "radiant_win",
        "duration",
        "game_mode",
        "lobby_type",
        "hero_id",
        "start_time",
        "version",
        "kills",
        "deaths",
        "assists",
        "skill",
        "xp_per_min",
        "gold_per_min",
        "hero_damage",
        "tower_damage",
        "hero_healing",
        "last_hits",
        "lane",
        "lane_role",
        "is_roaming",
        "cluster",
        "leaver_status",
        "party_size"
})
public class RecentMatches implements Serializable
{

    @JsonProperty("match_id")
    private Long matchId;
    @JsonProperty("player_slot")
    private Integer playerSlot;
    @JsonProperty("radiant_win")
    private Boolean radiantWin;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("game_mode")
    private Integer gameMode;
    @JsonProperty("lobby_type")
    private Integer lobbyType;
    @JsonProperty("hero_id")
    private Integer heroId;
    @JsonProperty("start_time")
    private Integer startTime;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("kills")
    private Integer kills;
    @JsonProperty("deaths")
    private Integer deaths;
    @JsonProperty("assists")
    private Integer assists;
    @JsonProperty("skill")
    private Integer skill;
    @JsonProperty("xp_per_min")
    private Integer xpPerMin;
    @JsonProperty("gold_per_min")
    private Integer goldPerMin;
    @JsonProperty("hero_damage")
    private Integer heroDamage;
    @JsonProperty("tower_damage")
    private Integer towerDamage;
    @JsonProperty("hero_healing")
    private Integer heroHealing;
    @JsonProperty("last_hits")
    private Integer lastHits;
    @JsonProperty("lane")
    private Integer lane;
    @JsonProperty("lane_role")
    private Integer laneRole;
    @JsonProperty("is_roaming")
    private Boolean isRoaming;
    @JsonProperty("cluster")
    private Integer cluster;
    @JsonProperty("leaver_status")
    private Integer leaverStatus;
    @JsonProperty("party_size")
    private Integer partySize;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1768167137209407896L;

    @JsonProperty("match_id")
    public Long getMatchId() {
        return matchId;
    }

    @JsonProperty("match_id")
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    @JsonProperty("player_slot")
    public Integer getPlayerSlot() {
        return playerSlot;
    }

    @JsonProperty("player_slot")
    public void setPlayerSlot(Integer playerSlot) {
        this.playerSlot = playerSlot;
    }

    @JsonProperty("radiant_win")
    public Boolean getRadiantWin() {
        return radiantWin;
    }

    @JsonProperty("radiant_win")
    public void setRadiantWin(Boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @JsonProperty("game_mode")
    public Integer getGameMode() {
        return gameMode;
    }

    @JsonProperty("game_mode")
    public void setGameMode(Integer gameMode) {
        this.gameMode = gameMode;
    }

    @JsonProperty("lobby_type")
    public Integer getLobbyType() {
        return lobbyType;
    }

    @JsonProperty("lobby_type")
    public void setLobbyType(Integer lobbyType) {
        this.lobbyType = lobbyType;
    }

    @JsonProperty("hero_id")
    public Integer getHeroId() {
        return heroId;
    }

    @JsonProperty("hero_id")
    public void setHeroId(Integer heroId) {
        this.heroId = heroId;
    }

    @JsonProperty("start_time")
    public Integer getStartTime() {
        return startTime;
    }

    @JsonProperty("start_time")
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonProperty("kills")
    public Integer getKills() {
        return kills;
    }

    @JsonProperty("kills")
    public void setKills(Integer kills) {
        this.kills = kills;
    }

    @JsonProperty("deaths")
    public Integer getDeaths() {
        return deaths;
    }

    @JsonProperty("deaths")
    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    @JsonProperty("assists")
    public Integer getAssists() {
        return assists;
    }

    @JsonProperty("assists")
    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    @JsonProperty("skill")
    public Integer getSkill() {
        return skill;
    }

    @JsonProperty("skill")
    public void setSkill(Integer skill) {
        this.skill = skill;
    }

    @JsonProperty("xp_per_min")
    public Integer getXpPerMin() {
        return xpPerMin;
    }

    @JsonProperty("xp_per_min")
    public void setXpPerMin(Integer xpPerMin) {
        this.xpPerMin = xpPerMin;
    }

    @JsonProperty("gold_per_min")
    public Integer getGoldPerMin() {
        return goldPerMin;
    }

    @JsonProperty("gold_per_min")
    public void setGoldPerMin(Integer goldPerMin) {
        this.goldPerMin = goldPerMin;
    }

    @JsonProperty("hero_damage")
    public Integer getHeroDamage() {
        return heroDamage;
    }

    @JsonProperty("hero_damage")
    public void setHeroDamage(Integer heroDamage) {
        this.heroDamage = heroDamage;
    }

    @JsonProperty("tower_damage")
    public Integer getTowerDamage() {
        return towerDamage;
    }

    @JsonProperty("tower_damage")
    public void setTowerDamage(Integer towerDamage) {
        this.towerDamage = towerDamage;
    }

    @JsonProperty("hero_healing")
    public Integer getHeroHealing() {
        return heroHealing;
    }

    @JsonProperty("hero_healing")
    public void setHeroHealing(Integer heroHealing) {
        this.heroHealing = heroHealing;
    }

    @JsonProperty("last_hits")
    public Integer getLastHits() {
        return lastHits;
    }

    @JsonProperty("last_hits")
    public void setLastHits(Integer lastHits) {
        this.lastHits = lastHits;
    }

    @JsonProperty("lane")
    public Integer getLane() {
        return lane;
    }

    @JsonProperty("lane")
    public void setLane(Integer lane) {
        this.lane = lane;
    }

    @JsonProperty("lane_role")
    public Integer getLaneRole() {
        return laneRole;
    }

    @JsonProperty("lane_role")
    public void setLaneRole(Integer laneRole) {
        this.laneRole = laneRole;
    }

    @JsonProperty("is_roaming")
    public Boolean getIsRoaming() {
        return isRoaming;
    }

    @JsonProperty("is_roaming")
    public void setIsRoaming(Boolean isRoaming) {
        this.isRoaming = isRoaming;
    }

    @JsonProperty("cluster")
    public Integer getCluster() {
        return cluster;
    }

    @JsonProperty("cluster")
    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    @JsonProperty("leaver_status")
    public Integer getLeaverStatus() {
        return leaverStatus;
    }

    @JsonProperty("leaver_status")
    public void setLeaverStatus(Integer leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    @JsonProperty("party_size")
    public Integer getPartySize() {
        return partySize;
    }

    @JsonProperty("party_size")
    public void setPartySize(Integer partySize) {
        this.partySize = partySize;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}