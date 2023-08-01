package gripe._90.fulleng;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import appeng.core.definitions.BlockDefinition;

import gripe._90.fulleng.block.FullBlock;

class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer) {
        block(consumer, FullblockEnergistics.TERMINAL_BLOCK);
        block(consumer, FullblockEnergistics.CRAFTING_TERMINAL_BLOCK);
        block(consumer, FullblockEnergistics.PATTERN_ENCODING_TERMINAL_BLOCK);
        block(consumer, FullblockEnergistics.PATTERN_ACCESS_TERMINAL_BLOCK);

        block(consumer, FullblockEnergistics.STORAGE_MONITOR_BLOCK);
        block(consumer, FullblockEnergistics.CONVERSION_MONITOR_BLOCK);
    }

    private void block(Consumer<FinishedRecipe> consumer, BlockDefinition<? extends FullBlock<?>> block) {
        var part = block.block().getEquivalentPart();
        var partId = part.id().getPath();

        ShapelessRecipeBuilder.shapeless(block).requires(part).unlockedBy("has_" + partId, has(part))
                .save(consumer, FullblockEnergistics.makeId("terminals/block_" + partId + "_from_part"));
        ShapelessRecipeBuilder.shapeless(part).requires(block).unlockedBy("has_" + partId, has(part))
                .save(consumer, FullblockEnergistics.makeId("terminals/part_" + partId + "_from_block"));
    }
}
