Feature: OrangeHRM Login

  Background:
    Given kullanıcı OrangeHRM giriş sayfasındadır

  @smoke
  Scenario: Geçerli bilgilerle giriş başarılı
    When kullanıcı "Admin" ve "admin123" bilgileriyle giriş yapar
    Then dashboard sayfası görüntülenir