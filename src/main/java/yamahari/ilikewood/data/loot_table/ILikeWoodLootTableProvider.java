package yamahari.ilikewood.data.loot_table;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import yamahari.ilikewood.ILikeWood;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ILikeWoodLootTableProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator dataGenerator;
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> lootTables = ImmutableList.of(Pair.of(ILikeWoodBlockLootTables::new, LootParameterSets.BLOCK));

    public ILikeWoodLootTableProvider(DataGenerator dataGeneratorIn) {
        this.dataGenerator = dataGeneratorIn;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void act(DirectoryCache cache) {
        Path path = this.dataGenerator.getOutputFolder();
        Map<ResourceLocation, LootTable> map = Maps.newHashMap();
        this.lootTables.forEach((lootParameterSetPair) -> lootParameterSetPair.getFirst().get().accept((resourceLocation, builder) -> {
            if (map.put(resourceLocation, builder.setParameterSet(lootParameterSetPair.getSecond()).build()) != null) {
                ILikeWood.logger.error("Duplicate loot table " + resourceLocation);
            }
        }));
        ValidationResults validationresults = new ValidationResults();

        map.forEach((resourceLocation, lootTable) -> LootTableManager.func_215302_a(validationresults, resourceLocation, lootTable, map::get));
        Multimap<String, String> multimap = validationresults.getProblems();
        if (!multimap.isEmpty()) {
            multimap.forEach((s, s1) -> ILikeWood.logger.warn("Found validation problem in " + s + ": " + s1));
            ILikeWood.logger.error("Failed to validate loot tables, see logs");
        } else {
            map.forEach((resourceLocation, lootTable) -> {
                try {
                    IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path.resolve("data/" + resourceLocation.getNamespace() + "/loot_tables/" + resourceLocation.getPath() + ".json"));
                } catch (IOException ioexception) {
                    ILikeWood.logger.error("Couldn't save loot table {}", path.resolve("data/" + resourceLocation.getNamespace() + "/loot_tables/" + resourceLocation.getPath() + ".json"), ioexception);
                }

            });
        }
    }

    @Override
    @Nonnull
    public String getName() {
        return "ILikeWood - LootTables";
    }
}
