/*
 * Hivemall: Hive scalable Machine Learning Library
 *
 * Copyright (C) 2013
 *   National Institute of Advanced Industrial Science and Technology (AIST)
 *   Registration Number: H25PRO-1520
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package hivemall.io;

import hivemall.utils.collections.IMapIterator;

public abstract class PredictionModel {

    protected ModelUpdateHandler handler;

    public PredictionModel() {}

    public ModelUpdateHandler getUpdateHandler() {
        return handler;
    }

    public void setUpdateHandler(ModelUpdateHandler handler) {
        this.handler = handler;
    }

    protected final void onUpdate(final int feature, final float weight, final short clock) {
        if(handler != null) {
            handler.onUpdate(feature, weight, 1.f, clock);
        }
    }

    protected final void onUpdate(final int feature, final float weight, final float covar, final short clock) {
        if(handler != null) {
            handler.onUpdate(feature, weight, covar, clock);
        }
    }

    protected final void onUpdate(final Object feature, final WeightValue value) {
        if(handler != null) {
            float weight = value.get();
            short clock = value.getClock();
            if(value.hasCovariance()) {
                float covar = value.getCovariance();
                handler.onUpdate(feature, weight, covar, clock);
            } else {
                handler.onUpdate(feature, weight, 1.f, clock);
            }
        }
    }

    public abstract void configureClock();

    public abstract boolean hasClock();

    public abstract int size();

    public abstract boolean contains(Object feature);

    public abstract <T extends WeightValue> T get(Object feature);

    public abstract <T extends WeightValue> void set(Object feature, T value);

    public abstract float getWeight(Object feature);

    public abstract float getCovariance(Object feature);

    @Deprecated
    public abstract void setValue(Object feature, float weight);

    @Deprecated
    public abstract void setValue(Object feature, float weight, float covar);

    public abstract <K, V extends WeightValue> IMapIterator<K, V> entries();

}
