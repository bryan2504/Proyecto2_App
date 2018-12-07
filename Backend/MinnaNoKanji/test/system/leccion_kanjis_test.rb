require "application_system_test_case"

class LeccionKanjisTest < ApplicationSystemTestCase
  setup do
    @leccion_kanji = leccion_kanjis(:one)
  end

  test "visiting the index" do
    visit leccion_kanjis_url
    assert_selector "h1", text: "Leccion Kanjis"
  end

  test "creating a Leccion kanji" do
    visit leccion_kanjis_url
    click_on "New Leccion Kanji"

    fill_in "Explicacion", with: @leccion_kanji.explicacion
    fill_in "Extra", with: @leccion_kanji.extra
    fill_in "Imagen Kanji", with: @leccion_kanji.imagen_kanji
    fill_in "Imagen Trazos", with: @leccion_kanji.imagen_trazos
    fill_in "Kanji", with: @leccion_kanji.kanji_id
    fill_in "Leccion", with: @leccion_kanji.leccion
    fill_in "Numero", with: @leccion_kanji.numero
    fill_in "Significado", with: @leccion_kanji.significado
    click_on "Create Leccion kanji"

    assert_text "Leccion kanji was successfully created"
    click_on "Back"
  end

  test "updating a Leccion kanji" do
    visit leccion_kanjis_url
    click_on "Edit", match: :first

    fill_in "Explicacion", with: @leccion_kanji.explicacion
    fill_in "Extra", with: @leccion_kanji.extra
    fill_in "Imagen Kanji", with: @leccion_kanji.imagen_kanji
    fill_in "Imagen Trazos", with: @leccion_kanji.imagen_trazos
    fill_in "Kanji", with: @leccion_kanji.kanji_id
    fill_in "Leccion", with: @leccion_kanji.leccion
    fill_in "Numero", with: @leccion_kanji.numero
    fill_in "Significado", with: @leccion_kanji.significado
    click_on "Update Leccion kanji"

    assert_text "Leccion kanji was successfully updated"
    click_on "Back"
  end

  test "destroying a Leccion kanji" do
    visit leccion_kanjis_url
    page.accept_confirm do
      click_on "Destroy", match: :first
    end

    assert_text "Leccion kanji was successfully destroyed"
  end
end
