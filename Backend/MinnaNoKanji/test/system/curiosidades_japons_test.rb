require "application_system_test_case"

class CuriosidadesJaponsTest < ApplicationSystemTestCase
  setup do
    @curiosidades_japon = curiosidades_japons(:one)
  end

  test "visiting the index" do
    visit curiosidades_japons_url
    assert_selector "h1", text: "Curiosidades Japons"
  end

  test "creating a Curiosidades japon" do
    visit curiosidades_japons_url
    click_on "New Curiosidades Japon"

    fill_in "Explicacion", with: @curiosidades_japon.explicacion
    fill_in "Tipo", with: @curiosidades_japon.tipo
    fill_in "Url Imagen", with: @curiosidades_japon.url_imagen
    click_on "Create Curiosidades japon"

    assert_text "Curiosidades japon was successfully created"
    click_on "Back"
  end

  test "updating a Curiosidades japon" do
    visit curiosidades_japons_url
    click_on "Edit", match: :first

    fill_in "Explicacion", with: @curiosidades_japon.explicacion
    fill_in "Tipo", with: @curiosidades_japon.tipo
    fill_in "Url Imagen", with: @curiosidades_japon.url_imagen
    click_on "Update Curiosidades japon"

    assert_text "Curiosidades japon was successfully updated"
    click_on "Back"
  end

  test "destroying a Curiosidades japon" do
    visit curiosidades_japons_url
    page.accept_confirm do
      click_on "Destroy", match: :first
    end

    assert_text "Curiosidades japon was successfully destroyed"
  end
end
