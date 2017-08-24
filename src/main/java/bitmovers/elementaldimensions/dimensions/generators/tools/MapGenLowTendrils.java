package bitmovers.elementaldimensions.dimensions.generators.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

import static bitmovers.elementaldimensions.dimensions.generators.tools.GeneratorTools.getBlockState;
import static bitmovers.elementaldimensions.dimensions.generators.tools.GeneratorTools.setBlockState;

public class MapGenLowTendrils {
    private int range = 8;
    private Random rand = new Random();
    private IBlockState baseBlock;

    public MapGenLowTendrils(IBlockState baseBlock) {
        this.baseBlock = baseBlock;
    }

    private void generateHeightMap(long seed, int chunkX, int chunkZ, ChunkPrimer primer, double xx, double yy, double zz, float p_151541_12_, float p_151541_13_, float p_151541_14_, int p_151541_15_, int p_151541_16_, double p_151541_17_) {
        double centerX = (chunkX * 16 + 8);
        double centerZ = (chunkZ * 16 + 8);
        float f3 = 0.0F;
        float f4 = 0.0F;
        Random random = new Random(seed);

        if (p_151541_16_ <= 0) {
            int j1 = this.range * 16 - 16;
            p_151541_16_ = j1 - random.nextInt(j1 / 4);
        }

        boolean flag2 = false;

        if (p_151541_15_ == -1) {
            p_151541_15_ = p_151541_16_ / 2;
            flag2 = true;
        }

        int k1 = random.nextInt(p_151541_16_ / 2) + p_151541_16_ / 4;

        boolean flag = random.nextInt(6) == 0;
        while (p_151541_15_ < p_151541_16_) {
            double d6 = 1.5D + (MathHelper.sin(p_151541_15_ * (float) Math.PI / p_151541_16_) * p_151541_12_ * 1.0F);
            double d7 = d6 * p_151541_17_;
            float f5 = MathHelper.cos(p_151541_14_);
            float f6 = MathHelper.sin(p_151541_14_);
            xx += (MathHelper.cos(p_151541_13_) * f5);
            yy += f6;
            zz += (MathHelper.sin(p_151541_13_) * f5);

            if (flag) {
                p_151541_14_ *= 0.92F;
            } else {
                p_151541_14_ *= 0.7F;
            }

            p_151541_14_ += f4 * 0.1F;
            p_151541_13_ += f3 * 0.1F;
            f4 *= 0.5F;
            f3 *= 0.45F;
            f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (!flag2 && p_151541_15_ == k1 && p_151541_12_ > 1.0F && p_151541_16_ > 0) {
                this.generateHeightMap(random.nextLong(), chunkX, chunkZ, primer, xx, yy, zz, random.nextFloat() * 0.5F + 0.5F, p_151541_13_ - ((float) Math.PI / 2F), p_151541_14_ / 3.0F, p_151541_15_, p_151541_16_, 1.0D);
                this.generateHeightMap(random.nextLong(), chunkX, chunkZ, primer, xx, yy, zz, random.nextFloat() * 0.5F + 0.5F, p_151541_13_ + ((float) Math.PI / 2F), p_151541_14_ / 3.0F, p_151541_15_, p_151541_16_, 1.0D);
                return;
            }

            if (flag2 || random.nextInt(4) != 0) {
                double cx = xx - centerX;
                double cz = zz - centerZ;
                double d10 = (p_151541_16_ - p_151541_15_);
                double d11 = (p_151541_12_ + 2.0F + 16.0F);

                if (cx * cx + cz * cz - d10 * d10 > d11 * d11) {
                    return;
                }

                if (xx >= centerX - 16.0D - d6 * 2.0D && zz >= centerZ - 16.0D - d6 * 2.0D && xx <= centerX + 16.0D + d6 * 2.0D && zz <= centerZ + 16.0D + d6 * 2.0D) {
                    int i7 = (int) (xx - d6);
                    int i4 = (xx - d6 < i7 ? i7 - 1 : i7) - chunkX * 16 - 1;
                    int i6 = (int) (xx + d6);
                    int l1 = (xx + d6 < i6 ? i6 - 1 : i6) - chunkX * 16 + 1;
                    int i5 = (int) (yy - d7);
                    int j4 = (yy - d7 < i5 ? i5 - 1 : i5) - 1;
                    int i3 = (int) (yy + d7);
                    int i2 = (yy + d7 < i3 ? i3 - 1 : i3) + 1;
                    int i1 = (int) (zz - d6);
                    int k4 = (zz - d6 < i1 ? i1 - 1 : i1) - chunkZ * 16 - 1;
                    int i = (int) (zz + d6);
                    int j2 = (zz + d6 < i ? i - 1 : i) - chunkZ * 16 + 1;

                    if (i4 < 0) {
                        i4 = 0;
                    }

                    if (l1 > 16) {
                        l1 = 16;
                    }

                    if (j4 < 1) {
                        j4 = 1;
                    }

                    if (i2 > 248) {
                        i2 = 248;
                    }

                    if (k4 < 0) {
                        k4 = 0;
                    }

                    if (j2 > 16) {
                        j2 = 16;
                    }

                    for (int xxx = i4; xxx < l1; ++xxx) {
                        double d13 = ((xxx + chunkX * 16) + 0.5D - xx) / d6;

                        for (int zzz = k4; zzz < j2; ++zzz) {
                            double d14 = ((zzz + chunkZ * 16) + 0.5D - zz) / d6;
                            int index = (xxx * 16 + zzz) * 256 + i2;

                            if (d13 * d13 + d14 * d14 < 1.0D) {
                                for (int l3 = i2 - 1; l3 >= j4; --l3) {
                                    double d12 = (l3 + 0.5D - yy) / d7;

                                    if (d12 > -0.7D && d13 * d13 + d12 * d12 + d14 * d14 < 1.0D) {
                                        IBlockState block = getBlockState(primer, index);

                                        if (block.getBlock() == Blocks.AIR || block == null) {
                                            setBlockState(primer, index, baseBlock);
                                        }
                                    }

                                    --index;
                                }
                            }
                        }

                        if (flag2) {
                            break;
                        }
                    }
                }
            }
            ++p_151541_15_;
        }
    }

    public void generate(World world, int chunkX, int chunkZ, ChunkPrimer primer) {
        int k = this.range;
        this.rand.setSeed(world.getSeed() * 13 + 527);
        long l = this.rand.nextLong();
        long i1 = this.rand.nextLong();

        for (int cx = chunkX - k; cx <= chunkX + k; ++cx) {
            for (int cz = chunkZ - k; cz <= chunkZ + k; ++cz) {
                long l1 = cx * l;
                long i2 = cz * i1;
                this.rand.setSeed(l1 ^ i2 ^ world.getSeed());
                this.fillChunk(cx, cz, chunkX, chunkZ, primer);
            }
        }
    }

    private void fillChunk(int cx, int cz, int chunkX, int chunkZ, ChunkPrimer primer) {
        int i1 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);

        for (int j1 = 0; j1 < i1; ++j1) {
            double xx = (cx * 16 + this.rand.nextInt(16));
            double yy = this.rand.nextInt(this.rand.nextInt(30) + 55);
            double zz = (cz * 16 + this.rand.nextInt(16));
            int k1 = 1;

            if (this.rand.nextInt(4) == 0) {
                this.generateHeightMap(this.rand.nextLong(), chunkX, chunkZ, primer, xx, yy, zz, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
                k1 += this.rand.nextInt(4);
            }

            for (int l1 = 0; l1 < k1; ++l1) {
                float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

                if (this.rand.nextInt(10) == 0) {
                    f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
                }

                this.generateHeightMap(this.rand.nextLong(), chunkX, chunkZ, primer, xx, yy, zz, f2, f, f1, 0, 0, 1.0D);
            }
        }
    }

}
