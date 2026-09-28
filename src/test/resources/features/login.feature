Feature: OrangeHRM Login

  Background:
    Given kullanıcı OrangeHRM giriş sayfasındadır

  @smoke
  Scenario: Geçerli bilgilerle giriş başarılı
    When kullanıcı "Admin" ve "admin123" bilgileriyle giriş yapar
    Then dashboard sayfası görüntülenir

  Scenario Outline: Hatalı bilgilerle giriş başarısız
    When kullanıcı "<kullanici>" ve "<sifre>" bilgileriyle giriş yapar
    Then "Invalid credentials" hata mesajı görüntülenir

    Examples:
      | kullanici  | sifre       |
      | Admin      | yanlisSifre |
      | yanlisUser | admin123    |
      | yanlisUser | yanlisSifre |

  Scenario: Boş alanlarla giriş yapılmaz
    When kullanıcı "" ve "" bilgileriyle giriş yapar
    Then "Required" uyarısı görüntülenir