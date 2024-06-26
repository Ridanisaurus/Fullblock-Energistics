package gripe._90.fulleng.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import appeng.block.AEBaseBlockItem;
import appeng.core.definitions.AEParts;
import appeng.core.definitions.BlockDefinition;
import appeng.core.definitions.ItemDefinition;
import appeng.items.parts.PartItem;
import appeng.parts.reporting.AbstractDisplayPart;
import appeng.parts.reporting.AbstractMonitorPart;

import gripe._90.fulleng.FullblockEnergistics;
import gripe._90.fulleng.block.FullBlock;
import gripe._90.fulleng.block.MonitorBlock;
import gripe._90.fulleng.block.TerminalBlock;
import gripe._90.fulleng.block.entity.monitor.ConversionMonitorBlockEntity;
import gripe._90.fulleng.block.entity.monitor.StorageMonitorBlockEntity;
import gripe._90.fulleng.block.entity.terminal.CraftingTerminalBlockEntity;
import gripe._90.fulleng.block.entity.terminal.PatternAccessTerminalBlockEntity;
import gripe._90.fulleng.block.entity.terminal.PatternEncodingTerminalBlockEntity;
import gripe._90.fulleng.block.entity.terminal.StorageTerminalBlockEntity;
import gripe._90.fulleng.block.entity.terminal.TerminalBlockEntity;
import gripe._90.fulleng.integration.Addons;
import gripe._90.fulleng.integration.IntegrationBlockItem;
import gripe._90.fulleng.integration.requester.RequesterTerminalBlock;
import gripe._90.fulleng.integration.requester.RequesterTerminalBlockEntity;

public class FullEngBlocks {
    private static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();

    public static List<BlockDefinition<?>> getBlocks() {
        return Collections.unmodifiableList(BLOCKS);
    }

    // spotless:off
    public static final BlockDefinition<TerminalBlock<StorageTerminalBlockEntity>> TERMINAL = terminal(AEParts.TERMINAL);
    public static final BlockDefinition<TerminalBlock<CraftingTerminalBlockEntity>> CRAFTING_TERMINAL = terminal(AEParts.CRAFTING_TERMINAL);
    public static final BlockDefinition<TerminalBlock<PatternEncodingTerminalBlockEntity>> PATTERN_ENCODING_TERMINAL = terminal(AEParts.PATTERN_ENCODING_TERMINAL);
    public static final BlockDefinition<TerminalBlock<PatternAccessTerminalBlockEntity>> PATTERN_ACCESS_TERMINAL = terminal(AEParts.PATTERN_ACCESS_TERMINAL);

    public static final BlockDefinition<MonitorBlock<StorageMonitorBlockEntity>> STORAGE_MONITOR = monitor(AEParts.STORAGE_MONITOR);
    public static final BlockDefinition<MonitorBlock<ConversionMonitorBlockEntity>> CONVERSION_MONITOR = monitor(AEParts.CONVERSION_MONITOR);
    // spotless:on

    public static final BlockDefinition<FullBlock<RequesterTerminalBlockEntity>> REQUESTER_TERMINAL = block(
            "ME Requester Terminal",
            "requester_terminal",
            RequesterTerminalBlock::new,
            block -> new IntegrationBlockItem(block, Addons.REQUESTER));

    static <P extends AbstractMonitorPart, E extends StorageMonitorBlockEntity>
            BlockDefinition<MonitorBlock<E>> monitor(ItemDefinition<PartItem<P>> equivalentPart) {
        return block(
                equivalentPart.getEnglishName(),
                equivalentPart.id().getPath(),
                () -> new MonitorBlock<>(equivalentPart));
    }

    static <P extends AbstractDisplayPart, E extends TerminalBlockEntity> BlockDefinition<TerminalBlock<E>> terminal(
            ItemDefinition<PartItem<P>> equivalentPart) {
        return block(
                equivalentPart.getEnglishName(),
                equivalentPart.id().getPath(),
                () -> new TerminalBlock<>(equivalentPart));
    }

    static <T extends Block> BlockDefinition<T> block(String englishName, String id, Supplier<T> supplier) {
        return block(englishName, id, supplier, block -> new AEBaseBlockItem(block, new Item.Properties()));
    }

    static <T extends Block> BlockDefinition<T> block(
            String englishName,
            String id,
            Supplier<T> blockSupplier,
            Function<T, ? extends AEBaseBlockItem> itemFunction) {
        var block = blockSupplier.get();
        var item = itemFunction.apply(block);
        var definition = new BlockDefinition<>(englishName, FullblockEnergistics.makeId(id), block, item);
        BLOCKS.add(definition);
        return definition;
    }
}
