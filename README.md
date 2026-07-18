**FinalProject-Dibimbing-Batch4**

[![CI](https://github.com/lamriatamp/FinalProject-Dibimbing-Batch4/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/lamriatamp/FinalProject-Dibimbing-Batch4/actions/workflows/ci.yml)

Deskripsi
- **Ringkasan**: Suite otomatisasi pengujian berbasis Java dan Gradle untuk contoh proyek FinalProject-Dibimbing (Batch 4). Menggunakan TestNG untuk runner, Selenium WebDriver untuk pengujian UI, dan Rest-Assured untuk pengujian API.

Prerequisites
- **Java**: JDK 11 atau lebih baru terpasang dan `JAVA_HOME` terkonfigurasi.
- **Gradle Wrapper**: Proyek menyertakan Gradle wrapper; tidak perlu menginstal Gradle secara global.
- **Browser & Driver**: Chrome/Firefox terinstal — WebDriver diunduh otomatis oleh WebDriverManager.

Menjalankan Tes
- **Jalankan semua tes (Windows):**

```bat
gradlew.bat test
```

- **Jalankan semua tes (Unix/macOS):**

```bash
./gradlew test
```

- **Menjalankan suite tertentu**: Proyek memakai properti Gradle `suite` untuk menunjuk file suite TestNG di `src/test/resources/suites/`. Contoh:

```bat
gradlew.bat test -Psuite=suite.xml
```

- **Menjalankan dengan environment**: Anda bisa meneruskan properti `env` jika diperlukan oleh konfigurasi:

```bat
gradlew.bat test -Psuite=suite.xml -Penv=stage
```

Struktur Proyek (singkat)
- **`src/main/java`**: kode sumber (jika ada utilitas shared).
- **`src/test/java`**: kode tes — kelas utilitas core ada di `src/test/java/core/` seperti `BaseApiTest.java`.
- **`src/test/resources`**: konfigurasi, `suites/` dan testdata.

File penting
- **Runner & konfigurasi Gradle**: [build.gradle.kts](build.gradle.kts)
- **Kelas dasar tes**: [src/test/java/core/BaseApiTest.java](src/test/java/core/BaseApiTest.java)
- **File suite default**: [src/test/resources/suites/suite.xml](src/test/resources/suites/suite.xml)

Kontribusi
- Buat branch baru untuk perubahan fitur atau perbaikan, buka pull request ke `main` dan sertakan deskripsi singkat langkah reproduksi.

Catatan Tambahan
- Proyek menggunakan TestNG (`org.testng:testng`) dan WebDriverManager untuk pengelolaan driver. Lihat `build.gradle.kts` untuk daftar dependensi lengkap.


