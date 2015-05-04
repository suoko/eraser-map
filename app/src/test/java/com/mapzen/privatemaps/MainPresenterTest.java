package com.mapzen.privatemaps;

import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Result;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPresenterTest {
    private MainPresenter presenter;
    private TestViewController controller;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenterImpl();
        controller = new TestViewController();
        presenter.setViewController(controller);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(presenter).isNotNull();
    }

    @Test
    public void onSearchResultsAvailable_shouldShowSearchResults() throws Exception {
        Result result = new Result();
        ArrayList<Feature> features = new ArrayList<>();
        result.setFeatures(features);
        presenter.onSearchResultsAvailable(result);
        assertThat(controller.features).isEqualTo(features);
    }

    @Test
    public void restoreViewState_shouldRestorePreviousSearchResults() throws Exception {
        Result result = new Result();
        ArrayList<Feature> features = new ArrayList<>();
        result.setFeatures(features);
        presenter.onSearchResultsAvailable(result);

        TestViewController newController = new TestViewController();
        presenter.setViewController(newController);
        presenter.restoreViewState();
        assertThat(newController.features).isEqualTo(features);
    }

    private class TestViewController implements ViewController {
        private List<Feature> features;

        @Override
        public void showSearchResults(@NotNull List<? extends Feature> features) {
            this.features = (List<Feature>) features;
        }
    }
}