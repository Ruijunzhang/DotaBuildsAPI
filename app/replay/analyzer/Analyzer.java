package replay.analyzer;

import models.dtos.Buildsinfo;
import models.dtos.Item;
import models.dtos.ReplayBuildsInfo;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import skadistats.clarity.Clarity;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static skadistats.clarity.wire.common.proto.DotaUserMessages.DOTA_COMBATLOG_TYPES.DOTA_COMBATLOG_PURCHASE;

public class Analyzer {

    private HashMap<String, Buildsinfo> heroItemMap =  new HashMap<>();
    private final PeriodFormatter GAMETIME_FORMATTER = buildPeriodFormatter();

    public  ReplayBuildsInfo getReplayBuildsInfoList(String replay)throws IOException {

        new SimpleRunner(new MappedFileSource(replay)).runWith(this);
        return generateReplayBuildsInfoList(Long.toString(Clarity.infoForSource(new MappedFileSource(replay)).getGameInfo().getDota().getMatchId()));
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {

        if(cle.getType() == DOTA_COMBATLOG_PURCHASE){
            if(heroItemMap.containsKey(getTargetNameCompiled(cle))){

                heroItemMap.get(getTargetNameCompiled(cle)).getItem().add(generateItem(cle));
            }else {
                Buildsinfo buildsInfo = new Buildsinfo();
                List<Item> itemList = new ArrayList<Item>();

                itemList.add(generateItem(cle));

                buildsInfo.setItem(itemList);
                buildsInfo.setLocalizedName(getTargetNameCompiled(cle));

                heroItemMap.put(getTargetNameCompiled(cle), buildsInfo);
            }
        }
    }

    private ReplayBuildsInfo generateReplayBuildsInfoList(String matchId){

        ReplayBuildsInfo replayBuildsInfo  = new ReplayBuildsInfo();
        replayBuildsInfo.setBuildsinfo(new ArrayList<>(heroItemMap.values()));
        replayBuildsInfo.setMatchid(matchId);
        return  replayBuildsInfo;
    }

    private Item generateItem(CombatLogEntry cle){
        Item item = new Item();
        item.setName(cle.getValueName());
        item.setTime(generateTimeInfo(cle));
        return  item;
    }

    private String compileName(String attackerName, boolean isIllusion) {
        return attackerName != null ? attackerName + (isIllusion ? " (illusion)" : "") : "UNKNOWN";
    }

    private String getTargetNameCompiled(CombatLogEntry cle) {
        return compileName(cle.getTargetName(), cle.isTargetIllusion());
    }

    private String generateTimeInfo(CombatLogEntry cle){
        return  "[" + GAMETIME_FORMATTER.print(Duration.millis((int) (1000.0f * cle.getTimestamp())).toPeriod()) + "]";
    }

    private PeriodFormatter buildPeriodFormatter(){
        return new PeriodFormatterBuilder()
                .minimumPrintedDigits(2)
                .printZeroAlways()
                .appendHours()
                .appendLiteral(":")
                .appendMinutes()
                .appendLiteral(":")
                .appendSeconds()
                .appendLiteral(".")
                .appendMillis3Digit()
                .toFormatter();
    }
}
