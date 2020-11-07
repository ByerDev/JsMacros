package xyz.wagyourtail.jsmacros.api.functions;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import xyz.wagyourtail.jsmacros.access.IChatHud;
import xyz.wagyourtail.jsmacros.api.helpers.TextHelper;
import xyz.wagyourtail.jsmacros.extensionbase.Functions;

import java.util.List;

/**
 * Functions for interacting with chat.
 * 
 * An instance of this class is passed to scripts as the {@code chat} variable.
 * 
 * @author Wagyourtail
 *
 */
public class FChat extends Functions {
    
    public FChat(String libName) {
        super(libName);
    }
    
    public FChat(String libName, List<String> excludeLanguages) {
        super(libName, excludeLanguages);
    }
    
    /**
     * Log to player chat.
     * 
     * @since 1.1.3
     * 
     * @param message
     */
    public void log(Object message) {
        mc.execute(() -> {
            if (message instanceof TextHelper) {
                logInternal((TextHelper)message);
            } else if (message != null) {
                logInternal(message.toString());
            }
        });
    }
    
    private static void logInternal(String message) {
        if (message != null) {
            LiteralText text = new LiteralText(message);
            ((IChatHud)mc.inGameHud.getChatHud()).jsmacros_addMessageBypass(text);
        }
    }
    
    private static void logInternal(TextHelper text) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ((IChatHud)mc.inGameHud.getChatHud()).jsmacros_addMessageBypass(text.getRaw());
    }
    
    /**
     * Say to server as player.
     * 
     * @since 1.0.0
     * 
     * @param message
     */
    public void say(String message) {
        if (message != null) {
            mc.player.sendChatMessage(message);
        }
    }
    
    /**
     * Display a Title to the player.
     * 
     * @since 1.2.1
     * 
     * @param title
     * @param subtitle
     * @param fadeIn
     * @param remain
     * @param fadeOut
     */
    public void title(Object title, Object subtitle, int fadeIn, int remain, int fadeOut) {
        Text titlee = null;
        Text subtitlee = null;
        if (title instanceof TextHelper) titlee = ((TextHelper) title).getRaw();
        else if (title != null) titlee = new LiteralText(title.toString());
        if (subtitle instanceof TextHelper) subtitlee = ((TextHelper) subtitle).getRaw();
        else if (subtitle != null) subtitlee = new LiteralText(subtitle.toString());
        if (title != null)
            mc.inGameHud.setTitles(titlee, null, fadeIn, remain, fadeOut);
        if (subtitle != null)
            mc.inGameHud.setTitles(null, subtitlee, fadeIn, remain, fadeOut);
        if (title == null && subtitle == null)
            mc.inGameHud.setTitles(null, null, fadeIn, remain, fadeOut);
    }
    
    /**
     * Display the smaller title that's above the actionbar.
     * 
     * @since 1.2.1
     * 
     * @param text
     * @param tinted
     */
    public void actionbar(Object text, boolean tinted) {
        Text textt = null;
        if (text instanceof TextHelper) textt = ((TextHelper) text).getRaw();
        else if (text != null) textt = new LiteralText(text.toString());
        mc.inGameHud.setOverlayMessage(textt, tinted);
    }
    
    /**
     * Display a toast.
     * 
     * @since 1.2.5
     * 
     * @param title
     * @param desc
     */
    public void toast(Object title, Object desc) {
        ToastManager t = mc.getToastManager();
        if (t != null) {
            Text titlee = (title instanceof TextHelper) ? ((TextHelper) title).getRaw() : title != null ? new LiteralText(title.toString()) : null;
            Text descc = (desc instanceof TextHelper) ? ((TextHelper) desc).getRaw() : desc != null ? new LiteralText(desc.toString()) : null;
            if (titlee != null) t.add(SystemToast.create(mc, null, titlee, descc));
        }
    }
    
    /**
     * Creates a {@link xyz.wagyourtail.jsmacros.api.helpers.TextHelper TextHelper} for use where you need one and not a string.
     * 
     * @see xyz.wagyourtail.jsmacros.api.helpers.TextHelper
     * @since 1.1.3
     * 
     * @param content
     * @return a new {@link xyz.wagyourtail.jsmacros.api.helpers.TextHelper TextHelper}
     */
    public TextHelper createTextHelperFromString(String content) {
        return new TextHelper(new LiteralText(content));
    }
    
    /**
     * Create a  {@link xyz.wagyourtail.jsmacros.api.helpers.TextHelper TextHelper} for use where you need one and not a string.
     * 
     * @see xyz.wagyourtail.jsmacros.api.helpers.TextHelper
     * @since 1.1.3
     * 
     * @param json
     * @return a new {@link xyz.wagyourtail.jsmacros.api.helpers.TextHelper TextHelper}
     */
    public TextHelper createTextHelperFromJSON(String json) {
        return new TextHelper(json);
    }
}
