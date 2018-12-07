require "application_system_test_case"

class HiraganasTest < ApplicationSystemTestCase
  setup do
    @hiragana = hiraganas(:one)
  end

  test "visiting the index" do
    visit hiraganas_url
    assert_selector "h1", text: "Hiraganas"
  end

  test "creating a Hiragana" do
    visit hiraganas_url
    click_on "New Hiragana"

    fill_in "Explicacion", with: @hiragana.explicacion
    fill_in "Leccion", with: @hiragana.leccion
    click_on "Create Hiragana"

    assert_text "Hiragana was successfully created"
    click_on "Back"
  end

  test "updating a Hiragana" do
    visit hiraganas_url
    click_on "Edit", match: :first

    fill_in "Explicacion", with: @hiragana.explicacion
    fill_in "Leccion", with: @hiragana.leccion
    click_on "Update Hiragana"

    assert_text "Hiragana was successfully updated"
    click_on "Back"
  end

  test "destroying a Hiragana" do
    visit hiraganas_url
    page.accept_confirm do
      click_on "Destroy", match: :first
    end

    assert_text "Hiragana was successfully destroyed"
  end
end
