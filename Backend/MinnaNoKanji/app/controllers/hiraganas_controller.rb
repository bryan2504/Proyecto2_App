class HiraganasController < ApplicationController
  before_action :set_hiragana, only: [:show, :edit, :update, :destroy]

  # GET /hiraganas
  # GET /hiraganas.json
  def index
    @hiraganas = Hiragana.all
  end

  # GET /hiraganas/1
  # GET /hiraganas/1.json
  def show
  end

  # GET /hiraganas/new
  def new
    @hiragana = Hiragana.new
  end

  # GET /hiraganas/1/edit
  def edit
  end

  # POST /hiraganas
  # POST /hiraganas.json
  def create
    @hiragana = Hiragana.new(hiragana_params)

    respond_to do |format|
      if @hiragana.save
        format.html { redirect_to @hiragana, notice: 'Hiragana was successfully created.' }
        format.json { render :show, status: :created, location: @hiragana }
      else
        format.html { render :new }
        format.json { render json: @hiragana.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /hiraganas/1
  # PATCH/PUT /hiraganas/1.json
  def update
    respond_to do |format|
      if @hiragana.update(hiragana_params)
        format.html { redirect_to @hiragana, notice: 'Hiragana was successfully updated.' }
        format.json { render :show, status: :ok, location: @hiragana }
      else
        format.html { render :edit }
        format.json { render json: @hiragana.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /hiraganas/1
  # DELETE /hiraganas/1.json
  def destroy
    @hiragana.destroy
    respond_to do |format|
      format.html { redirect_to hiraganas_url, notice: 'Hiragana was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_hiragana
      @hiragana = Hiragana.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def hiragana_params
      params.require(:hiragana).permit(:leccion, :explicacion)
    end
end
