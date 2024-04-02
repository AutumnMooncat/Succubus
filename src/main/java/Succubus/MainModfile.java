package Succubus;

import Succubus.cardmods.AbstractInfusion;
import Succubus.cards.cardvars.*;
import Succubus.cards.interfaces.GlowAdjacentCard;
import Succubus.icons.IconContainer;
import Succubus.patches.GlowChangePatch;
import Succubus.powers.BracedPower;
import Succubus.powers.CharmPower;
import Succubus.powers.ExposedPower;
import Succubus.powers.StaggerPower;
import Succubus.powers.interfaces.InfusionTriggerPower;
import Succubus.powers.interfaces.OnUpgradePower;
import Succubus.relics.AbstractEasyRelic;
import Succubus.ui.SheathedCardManager;
import Succubus.util.*;
import Succubus.vfx.ShaderTest;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.RelicType;
import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class MainModfile implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber, PostUpdateSubscriber, AddAudioSubscriber, OnPlayerDamagedSubscriber, StartGameSubscriber {

    public static final String modID = "Succubus";
    public static final Logger logger = LogManager.getLogger(MainModfile.class.getName());

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static final Color SUCCUBUS_ROSE_COLOR = CardHelper.getColor(255, 135, 159); //FF879F

    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulder2.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulder.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    public static final String ATTACK_S_ART = modID + "Resources/images/512/attack_pink4.png";
    public static final String SKILL_S_ART = modID + "Resources/images/512/skill_pink4.png";
    public static final String POWER_S_ART = modID + "Resources/images/512/power_pink4.png";
    public static final String ATTACK_L_ART = modID + "Resources/images/1024/attack_pink4.png";
    public static final String SKILL_L_ART = modID + "Resources/images/1024/skill_pink4.png";
    public static final String POWER_L_ART = modID + "Resources/images/1024/power_pink4.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/energy_text.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy_large.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG.png";

    public static final String BADGE_IMAGE = modID + "Resources/images/Badge.png";

    public static UIStrings uiStrings;
    public static String[] TEXT;
    public static String[] EXTRA_TEXT;
    private static final String AUTHOR = "Mistress Alison";

    public static final String ENABLE_CARD_BATTLE_TALK_SETTING = "enableCardBattleTalk";
    public static boolean enableCardBattleTalkEffect = false;

    public static final String CARD_BATTLE_TALK_PROBABILITY_SETTING = "cardTalkProbability";
    public static int cardTalkProbability = 10; //Out of 100

    public static final String ENABLE_DAMAGED_BATTLE_TALK_SETTING = "enableDamagedBattleTalk";
    public static boolean enableDamagedBattleTalkEffect = false;

    public static final String DAMAGED_BATTLE_TALK_PROBABILITY_SETTING = "damagedTalkProbability";
    public static int damagedTalkProbability = 20; //Out of 100

    public static final String ENABLE_PRE_BATTLE_TALK_SETTING = "enablePreBattleTalk";
    public static boolean enablePreBattleTalkEffect = false;

    public static final String PRE_BATTLE_TALK_PROBABILITY_SETTING = "preTalkProbability";
    public static int preTalkProbability = 50; //Out of 100

    public MainModfile() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheSuccubus.Enums.SUCCUBUS_ROSE_COLOR, SUCCUBUS_ROSE_COLOR, SUCCUBUS_ROSE_COLOR, SUCCUBUS_ROSE_COLOR,
                SUCCUBUS_ROSE_COLOR, SUCCUBUS_ROSE_COLOR, SUCCUBUS_ROSE_COLOR, SUCCUBUS_ROSE_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        MainModfile thismod = new MainModfile();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheSuccubus(TheSuccubus.characterStrings.NAMES[1], TheSuccubus.Enums.THE_SUCCUBUS),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheSuccubus.Enums.THE_SUCCUBUS);
        PotionLoader.loadContent();
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new Info());
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new SecondBlock());
        BaseMod.addDynamicVariable(new DynvarInterfaceManager());

        CustomIconHelper.addCustomIcon(IconContainer.FireIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.IceIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.BoltIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.WindIcon.get());

        new AutoAdd(modID)
                .packageFilter(modID+".cards")
                .setDefaultSeen(true)
                .cards();
    }

    private void loadLangKeywords(String language) {
        Gson gson = new Gson();
        Keyword[] keywords = null;
        try {
            String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
            keywords = gson.fromJson(json, Keyword[].class);
        } catch (GdxRuntimeException e) {
            if (!e.getMessage().startsWith("File not found:")) {
                throw e;
            }
        }

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                if(keyword.NAMES.length > 0 && !keyword.ID.isEmpty()) {
                    KeywordManager.setupKeyword(keyword.ID, keyword.NAMES[0]);
                }
            }
        }
    }

    @Override
    public void receiveEditKeywords() {
        String language = Settings.language.name().toLowerCase();
        loadLangKeywords("eng");
        if (!language.equals("eng")) {
            loadLangKeywords(language);
        }
    }

    private void loadLangStrings(String language) {
        String path = modID+"Resources/localization/" + language + "/";

        tryLoadStringsFile(CardStrings.class,path + "Cardstrings.json");
        tryLoadStringsFile(RelicStrings.class, path + "Relicstrings.json");
        tryLoadStringsFile(CharacterStrings.class, path + "Charstrings.json");
        tryLoadStringsFile(PowerStrings.class, path + "Powerstrings.json");
        tryLoadStringsFile(CardStrings.class, path + "CardModstrings.json");
        tryLoadStringsFile(CardStrings.class, path + "Chatterstrings.json");
        tryLoadStringsFile(UIStrings.class, path + "UIstrings.json");
        tryLoadStringsFile(PotionStrings.class, path +"Potionstrings.json");
        tryLoadStringsFile(UIStrings.class, path + "CardAugmentstrings.json");
    }

    private void tryLoadStringsFile(Class<?> stringType, String filepath) {
        try {
            BaseMod.loadCustomStringsFile(stringType, filepath);
        } catch (GdxRuntimeException e) {
            // Ignore file not found
            if (!e.getMessage().startsWith("File not found:")) {
                throw e;
            }
        }
    }

    @Override
    public void receiveEditStrings() {
        String language = Settings.language.name().toLowerCase();
        loadLangStrings("eng");
        if (!language.equals("eng")) {
            loadLangStrings(language);
        }
    }

    @Override
    public void receivePostInitialize() {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ModConfigs"));
        EXTRA_TEXT = uiStrings.EXTRA_TEXT;
        TEXT = uiStrings.TEXT;
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Load the Mod Badge
        Texture badgeTexture = TexLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, EXTRA_TEXT[0], AUTHOR, EXTRA_TEXT[1], settingsPanel);

        //Add Powers
        BaseMod.addPower(BracedPower.class, BracedPower.POWER_ID);
        BaseMod.addPower(StaggerPower.class, StaggerPower.POWER_ID);
        BaseMod.addPower(ExposedPower.class, ExposedPower.POWER_ID);
        BaseMod.addPower(CharmPower.class, CharmPower.POWER_ID);

        //Wide Potions
        if (Loader.isModLoaded("widepotions")) {
            WidePotionLoader.loadCrossoverContent();
        }

        //Chimera Cards
        if (Loader.isModLoaded("CardAugments")) {
            ChimeraHelper.registerAugments();
            ChimeraHelper.applyBans();
        }

        //Add Config stuff

        //Other Setup stuff
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Color.RED.cpy();
            @Override
            public boolean test(AbstractCard card) {
                if (Wiz.adp().hoveredCard instanceof GlowAdjacentCard && ((GlowAdjacentCard) Wiz.adp().hoveredCard).glowAdjacent(card)) {
                    if (GlowChangePatch.GlowCheckField.lastChecked.get(card) != Wiz.adp().hoveredCard) {
                        GlowChangePatch.GlowCheckField.lastChecked.set(card, Wiz.adp().hoveredCard);
                        if (((GlowAdjacentCard) Wiz.adp().hoveredCard).flashAdjacent(card)) {
                            card.superFlash(((GlowAdjacentCard) Wiz.adp().hoveredCard).getGlowColor(card));
                        }
                    }
                    return true;
                }
                GlowChangePatch.GlowCheckField.lastChecked.set(card, Wiz.adp().hoveredCard);
                return false;
            }

            @Override
            public Color getColor(AbstractCard card) {
                if (Wiz.adp().hoveredCard instanceof GlowAdjacentCard) {
                    return ((GlowAdjacentCard) Wiz.adp().hoveredCard).getGlowColor(card);
                }
                return c;
            }

            @Override
            public String glowID() {
                return makeID("AdjacentGlow");
            }
        });

        if (ShaderTest.test) {
            ScreenPostProcessor postProcessor = new ShaderTest();
            ScreenPostProcessorManager.addPostProcessor(postProcessor);
        }
    }

    public static Color getRainbowColor() {
        return new Color(
                (MathUtils.cosDeg((float)(System.currentTimeMillis() / 10L % 360L)) + 1.25F) / 2.3F,
                (MathUtils.cosDeg((float)((System.currentTimeMillis() + 1000L) / 10L % 360L)) + 1.25F) / 2.3F,
                (MathUtils.cosDeg((float)((System.currentTimeMillis() + 2000L) / 10L % 360L)) + 1.25F) / 2.3F,
                1.0f);
    }

    public static float time = 0f;
    @Override
    public void receivePostUpdate() {
        time += Gdx.graphics.getRawDeltaTime();
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(CustomSounds.SYNTH_START_KEY, CustomSounds.SYNTH_START_PATH);
        BaseMod.addAudio(CustomSounds.SYNTH_START_KEY2, CustomSounds.SYNTH_START_PATH2);
        BaseMod.addAudio(CustomSounds.SYNTH_END_KEY, CustomSounds.SYNTH_END_PATH);
        BaseMod.addAudio(CustomSounds.SYNTH_MIX_KEY, CustomSounds.SYNTH_MIX_PATH);
    }

    public static void infusionTrigger(AbstractInfusion infusion, int directAmount, int relicAmount) {
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof InfusionTriggerPower) {
                ((InfusionTriggerPower) p).infusionTrigger(infusion, directAmount);
            }
        }
    }

    public static void onUpgradeTrigger(AbstractCard c) {
        if (Wiz.adp() != null && Wiz.isInCombat() && Wiz.isCombatCard(c)) {
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof OnUpgradePower) {
                    ((OnUpgradePower) p).onUpgrade(c);
                }
            }
        }
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (damageInfo.type == DamageInfo.DamageType.NORMAL) {
            SheathedCardManager.playNextCard(damageInfo);
        }
        return i;
    }

    @Override
    public void receiveStartGame() {
        SheathedCardManager.EmptyCards.yeet();
    }
}
