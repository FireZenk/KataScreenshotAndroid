package com.karumi.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.karumi.screenshot.di.MainComponent;
import com.karumi.screenshot.di.MainModule;
import com.karumi.screenshot.model.SuperHero;
import com.karumi.screenshot.model.SuperHeroesRepository;
import com.karumi.screenshot.ui.view.SuperHeroDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Mockito.when;

/**
 * Created by Jorge Garrido Oval, aka firezenk on 25/03/17.
 * Project: KataScreenshotAndroid
 */

public class SuperHeroDetailTest extends ScreenshotTest {

    @Rule public DaggerMockRule<MainComponent> daggerRule =
            new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
                    new DaggerMockRule.ComponentSetter<MainComponent>() {
                        @Override public void setComponent(MainComponent component) {
                            SuperHeroesApplication app =
                                    (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();
                            app.setComponent(component);
                        }
                    });

    @Rule public ActivityTestRule<SuperHeroDetailActivity> activityRule =
            new ActivityTestRule<>(SuperHeroDetailActivity.class, true, false);

    @Mock SuperHeroesRepository repository;

    @Test public void showsAnySuperHero() {
        SuperHero superHero = givenASuperHero();

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test public void showsAnySuperHeroWithoutName() {
        SuperHero superHero = givenASuperHero("", givenASuperHero().getDescription(),
                givenASuperHero().isAvenger());

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test public void showsAnySuperHeroWithoutDescription() {
        SuperHero superHero = givenASuperHero(givenASuperHero().getName(), "",
                givenASuperHero().isAvenger());

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test public void showsAnySuperHeroWithLongName() {
        SuperHero superHero = givenASuperHeroWithALongName();

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test public void showsAnySuperHeroWithLongDescription() {
        SuperHero superHero = givenASuperHeroWithALongDescription();

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test public void showsAnAvenger() {
        SuperHero superHero = givenAnAvenger();

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    private SuperHeroDetailActivity startActivity(SuperHero superHero) {
        Intent intent = new Intent();
        intent.putExtra("super_hero_name_key", superHero.getName());
        return activityRule.launchActivity(intent);
    }

    private SuperHero givenASuperHeroWithALongDescription() {
        String superHeroName = "Super Hero Name";
        String superHeroDescription =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
                        + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
                        + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
                        + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
                        + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt "
                        + "mollit anim id est laborum.";
        boolean isAvenger = false;
        return givenASuperHero(superHeroName, superHeroDescription, isAvenger);
    }

    private SuperHero givenASuperHeroWithALongName() {
        String superHeroName =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
                        + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
                        + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
                        + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
                        + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt "
                        + "mollit anim id est laborum.";
        String superHeroDescription = "Description Super Hero";
        boolean isAvenger = false;
        return givenASuperHero(superHeroName, superHeroDescription, isAvenger);
    }

    private SuperHero givenAnAvenger() {
        return givenASuperHero("Super Hero Name", "Super Hero Description", true);
    }

    private SuperHero givenASuperHero() {
        return givenASuperHero("Super Hero Name", "Super Hero Description", false);
    }

    private SuperHero givenASuperHero(String superHeroName, String superHeroDescription,
                                      boolean isAvenger) {

        SuperHero superHero = new SuperHero(superHeroName, null, isAvenger, superHeroDescription);

        when(repository.getByName(superHeroName)).thenReturn(superHero);

        return superHero;
    }
}
