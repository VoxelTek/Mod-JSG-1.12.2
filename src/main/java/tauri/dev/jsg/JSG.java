package tauri.dev.jsg;

import tauri.dev.jsg.command.JSGCommands;
import tauri.dev.jsg.config.stargate.StargateDimensionConfig;
import tauri.dev.jsg.integration.OCWrapperInterface;
import tauri.dev.jsg.proxy.IProxy;
import tauri.dev.jsg.util.main.loader.JSGInit;
import tauri.dev.jsg.util.main.loader.JSGPreInit;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = JSG.MOD_ID, name = JSG.MOD_NAME, version = JSG.MOD_VERSION, acceptedMinecraftVersions = JSG.MC_VERSION, dependencies = "after:cofhcore@[4.6.0,);after:opencomputers;after:thermalexpansion;after:tconstruct")
public class JSG {

    // --------------------------------------------
    // CONSTANTS

    public static final String MOD_ID = "jsg";
    public static final String MOD_NAME = "Just Stargate Mod";
    public static final String MOD_VERSION = "@VERSION@";
    public static final int DATA_VERSION = 18;
    public static final String CONFIG_VERSION = "1.0";
    public static final String MC_VERSION = "@MCVERSION@";
    public static final String CLIENT = "tauri.dev.jsg.proxy.ProxyClient";
    public static final String SERVER = "tauri.dev.jsg.proxy.ProxyServer";
    public static final String AGS_PATH = "pastebin run pAqHB264";
    public static SoundCategory JSG_SOUNDS;

    // --------------------------------------------
    // VARIABLES

    public static Logger logger;
    public static OCWrapperInterface ocWrapper;
    public static boolean isThermalLoaded = false;
    public static String clientModPath;

    // --------------------------------------------
    // PROXY

    @SidedProxy(clientSide = JSG.CLIENT, serverSide = JSG.SERVER)
    public static IProxy proxy;

    // --------------------------------------------
    // INSTANCE

    @Instance(MOD_ID)
    public static JSG instance;

    // --------------------------------------------
    // Enable forge buckets

    static {
        FluidRegistry.enableUniversalBucket();
    }

    // --------------------------------------------
    // SHORTHAND

    public static void info(String string) {
        logger.info(string);
    }

    public static void warn(String string) {
        logger.warn(string);
    }

    public static void error(String string) {
        logger.error(string);
    }

    public static void debug(String string) {
        logger.debug(string);
    }

    // --------------------------------------------
    // USED IN ITEMS

    public static String getInProgress() {
        return TextFormatting.AQUA + "Work In Progress Item!";
    }

    // --------------------------------------------
    // REGISTER

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        JSGPreInit.preInit(event);
        JSG.proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        JSGInit.init(event);
        JSG.proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event){
        JSG.info("JSG loaded!");
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        JSGCommands.registerCommands(event);
        JSG.info("Successfully registered Commands!");
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) throws IOException {
        StargateDimensionConfig.update();
        JSG.info("Server started!");
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        JSG.info("Good bye! Thank you for using JSG: Resurrection :)");
    }
}