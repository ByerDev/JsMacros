package xyz.wagyourtail.jsmacros.client.api.helpers;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.wagyourtail.jsmacros.core.helpers.BaseHelper;

/**
 * @since 1.4.2
 */
public class CommandContextHelper extends BaseHelper<CommandContext<FabricClientCommandSource>> {
    public CommandContextHelper(CommandContext<FabricClientCommandSource> base) {
        super(base);
    }

    /**
     * @param name
     *
     * @return
     * @since 1.4.2
     * @throws CommandSyntaxException
     */
    public Object getArg(String name) throws CommandSyntaxException {
        Object arg = base.getArgument(name, Object.class);
        if (arg instanceof BlockStateArgument) {
            arg = new BlockDataHelper(((BlockStateArgument) arg).getBlockState(), null, null);
        } else if (arg instanceof Identifier) {
            arg = arg.toString();
        } else if (arg instanceof ItemStackArgument) {
            arg = new ItemStackHelper(((ItemStackArgument) arg).createStack(1, false));
        } else if (arg instanceof NbtElement) {
            arg = NBTElementHelper.resolve((NbtElement) arg);
        } else if (arg instanceof Text) {
            arg = new TextHelper((Text) arg);
        }
        return arg;
    }

    public CommandContextHelper getChild() {
        return new CommandContextHelper(base.getChild());
    }

    public StringRange getRange() {
        return base.getRange();
    }

    public String getInput() {
        return base.getInput();
    }
}
