package ru.arutyunyan.factory.settings;

import org.openqa.selenium.remote.AbstractDriverOptions;


public interface IBrowserSettings {

    AbstractDriverOptions<?> settings();
}